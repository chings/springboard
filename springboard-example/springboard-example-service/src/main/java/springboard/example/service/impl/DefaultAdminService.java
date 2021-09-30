package springboard.example.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import springboard.example.bean.Account;
import springboard.example.bean.Identity;
import springboard.example.bean.Role;
import springboard.example.bean.User;
import springboard.example.dao.AccountMapper;
import springboard.example.dao.IdentityMapper;
import springboard.example.dao.RoleMapper;
import springboard.example.dao.UserMapper;
import springboard.example.service.AdminService;
import springboard.mybatis.util.Queries;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@DubboService
public class DefaultAdminService implements AdminService {

    private static Logger log = LoggerFactory.getLogger(DefaultAdminService.class);

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    IdentityMapper identityMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;


    @DS("slave")
    @Override
    public Role getRole(long id) {
        return roleMapper.selectById(id);
    }

    @DS("slave")
    @Override
    public Page<Role> listRoles(@Nullable Long id, @Nullable String name, @Nullable String description, @Nullable Date createdTime0, @Nullable Date createdTime1, int... pagination) {
        LambdaQueryWrapper<Role> criteria = new QueryWrapper<Role>().lambda();
        criteria.eq(Role::getType, Identity.Type.ROLE);
        if(id != null) criteria.eq(Role::getId, id);
        if(name != null) criteria.like(Role::getName, name);
        if(description != null) criteria.like(Role::getDescription, description);
        if(createdTime0 != null) criteria.ge(Role::getCreatedTime, createdTime0);
        if(createdTime1 != null) criteria.lt(Role::getCreatedTime, createdTime1);
        return Queries.paginate(() -> roleMapper.selectList(criteria), pagination);
    }

    @Override
    public Role createRole(Role role) {
        boolean ok = roleMapper.insert(role) == 1;
        return ok ? role : null;
    }

    @Override
    public boolean updateRole(long id, @Nullable String name, @Nullable String description) {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        role.setDescription(description);
        role.setUpdatedTime(new Date());
        return roleMapper.updateById(role) == 1;
    }

    @Transactional
    @Override
    public boolean purgeRole(long id) {
        identityMapper.unsetAllPermissions(id);
        return roleMapper.deleteById(id) == 1;
    }

    String encodePassword(String password) {
        return StringUtils.hasText(password) ? passwordEncoder.encode(password) : null;
    }

    @DS("slave")
    @Override
    public User getUser(long id) {
        return userMapper.selectById(id);
    }

    @DS("slave")
    @Override
    public User findUser(String username) {
        return userMapper.selectByUsername(username);
    }

    @DS("slave")
    @Override
    public User findUser(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if(user == null) return null;
        if(!passwordEncoder.matches(password, user.getAccount().getEncodedPassword())) throw new BadCredentialsException("Bad Password: " + password);
        return user;
    }

    @DS("slave")
    @Override
    public Page<User> listUsers(@Nullable Long id, @Nullable Account.Status status, @Nullable String username, @Nullable String name, @Nullable String description, @Nullable Date createdTime0, @Nullable Date createdTime1, int... pagination) {
        return Queries.paginate(() -> userMapper.selectList(id, status, username, name, description, createdTime0, createdTime1), pagination);
    }

    @Transactional
    @Override
    public User createUser(User user) {
        boolean ok = identityMapper.insert(user) == 1;

        Account account = user.getAccount();
        account.setUserId(user.getId());
        String password = account.getPassword();
        account.setPassword(encodePassword(password));
        ok |= accountMapper.insert(account) == 1;
        account.setPassword(password);

        return ok ? user : null;
    }

    @Override
    public boolean updateUser(long id, @Nullable String name, @Nullable String description) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setDescription(description);
        user.setUpdatedTime(new Date());
        return userMapper.updateById(user) == 1;
    }

    @Transactional
    @Override
    public boolean purgeUser(long id) {
        identityMapper.unsetAllPermissions(id);
        roleMapper.unsetAllRoles(id);
        accountMapper.deleteByUserId(id);
        return identityMapper.deleteById(id) == 1;
    }

    @DS("slave")
    @Override
    public Account findUserAccount(long userId) {
        return accountMapper.selectByUserId(userId);
    }

    @Override
    public boolean updateUserAccount(long userId, @Nullable Account.Status status, String username, @Nullable String password) {
        Account account = new Account();
        account.setStatus(status);
        account.setPassword(encodePassword(password));
        account.setUpdatedTime(new Date());
        return accountMapper.update(account, new QueryWrapper<Account>().lambda().eq(Account::getUserId, userId)) == 1;
    }

    @Override
    public boolean touchUserAccount(long userId, @Nullable Date lastLoggedInTime, @Nullable String lastLoggedInAddr) {
        Account account = new Account();
        account.setLastLoggedInTime(lastLoggedInTime);
        account.setLastLoggedInAddr(lastLoggedInAddr);
        return accountMapper.update(account, new QueryWrapper<Account>().lambda().eq(Account::getUserId, userId)) == 1;
    }

    @DS("slave")
    @Override
    public List<Role> findUserRoles(long userId) {
        return roleMapper.findRoles(userId);
    }

    @Transactional
    @Override
    public boolean setUserRoles(long userId, long... roleIds) {
        boolean ok = false;
        for(long roleId : roleIds) {
            ok |= roleMapper.setRole(userId, roleId) == 1;
        }
        return ok;
    }

    @Transactional
    @Override
    public boolean unsetUserRoles(long userId, long... roleIds) {
        if(roleIds.length == 0) return roleMapper.unsetAllRoles(userId) > 0;
        boolean ok = false;
        for(long roleId : roleIds) {
            ok |= roleMapper.unsetRole(userId, roleId) == 1;
        }
        return ok;
    }

    @DS("slave")
    @Override
    public List<String> findPermissions(long identityId) {
        return identityMapper.findPermissions(identityId);
    }

    @DS("slave")
    @Override
    public List<String> findUserPermissions(long userId) {
        Set<Long> identityIds = new HashSet<>();
        identityIds.add(userId);
        identityIds.addAll(findUserRoles(userId).stream().map(role -> role.getId()).collect(Collectors.toSet()));
        return identityMapper.findAllPermissions(identityIds);
    }

    @Transactional
    @Override
    public boolean grantPermissions(long identityId, String... permissions) {
        boolean ok = false;
        for(String permission : permissions) {
            ok |= identityMapper.setPermission(identityId, permission) == 1;
        }
        return ok;
    }

    @Transactional
    @Override
    public boolean revokePermissions(long identityId, String... permissions) {
        if(permissions.length == 0) return identityMapper.unsetAllPermissions(identityId) > 0;
        boolean ok = false;
        for(String permission : permissions) {
            ok |= identityMapper.unsetPermission(identityId, permission) == 1;
        }
        return ok;
    }

    @Override
    public Object findSubject(String principal) {
        return findUser(principal);
    }

    @Override
    public Object findSubject(String principal, String credential) {
        return findUser(principal, credential);
    }

    @Override
    public Set<String> findRoles(String principal) {
        User user = findUser(principal);
        if(user == null) return null;
        return findUserRoles(user.getId()).stream().map(role -> role.getName()).collect(Collectors.toSet());
    }

    @Override
    public Set<String> findPermissions(String principal) {
        User user = findUser(principal);
        if(user == null) return null;
        return findUserPermissions(user.getId()).stream().collect(Collectors.toSet());
    }

}
