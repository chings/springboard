package springboard.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import springboard.example.dao.RoleMapper;
import springboard.example.dao.UserMapper;
import springboard.example.model.AdminService;
import springboard.example.model.Role;
import springboard.example.model.User;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Service
public class DefaultAdminService implements AdminService {

    private static Logger log = LoggerFactory.getLogger(DefaultAdminService.class);

    public static final int DEFAULT_PAGE_SIZE = 20;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Role createRole(Role role) {
        boolean ok = roleMapper.insert(role) == 1;
        return ok ? role : null;
    }

    @Transactional
    @Override
    public User createUser(User user) {
        Role role = new Role();
        role.setType(user.getType());
        role.setName(user.getName());
        role.setCreatedTime(user.getCreatedTime());
        role = createRole(role);
        if(role == null) return null;

        user.setId(role.getId());
        String password = user.getPassword();
        if(StringUtils.hasText(password)) user.setPassword(passwordEncoder.encode(password));
        boolean ok = userMapper.insert(user) == 1;
        user.setPassword(password);
        return ok ? user : null;
    }

    @DS("slave")
    @Override
    public Role getRole(long id) {
        return roleMapper.selectById(id);
    }

    @DS("slave")
    @Override
    public User getUser(long id) {
        return userMapper.selectById(id);
    }

    @DS("slave")
    @Override
    public User getUser(String username) {
        return userMapper.selectByUsername(username);
    }

    @DS("slave")
    @Override
    public User getUser(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if(user == null) return null;
        if(!passwordEncoder.matches(password, user.getPassword())) return null;
        return user;
    }

    @DS("slave")
    @Override
    public List<Role> findRoles(@Nullable Long id, @Nullable Role.Type type, @Nullable String name, @Nullable Date createdTime0, @Nullable Date createdTime1, int... pagination) {
        Wrapper<Role> criteria = new EntityWrapper<>();
        if(id != null) criteria.eq("id", id);
        if(type != null) criteria.eq("type", type);
        if(name != null) criteria.like("name", name);
        if(createdTime0 != null) criteria.ge("created_time", createdTime0);
        if(createdTime1 != null) criteria.lt("created_time", createdTime1);
        Integer pageNum = pagination.length > 0 ? pagination[0]: null;
        Integer pageSize = pagination.length > 1 ? pagination[1] : DEFAULT_PAGE_SIZE;
        return pageNum != null ?
                PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> roleMapper.selectList(criteria)) :
                roleMapper.selectList(criteria);
    }

    @DS("slave")
    @Override
    public List<User> findUsers(@Nullable Long id, @Nullable User.Status status, @Nullable String username, @Nullable String name, @Nullable Date createdTime0, @Nullable Date createdTime1, int... pagination) {
        Integer pageNum = pagination.length > 0 ? pagination[0]: null;
        Integer pageSize = pagination.length > 1 ? pagination[1] : DEFAULT_PAGE_SIZE;
        return pageNum != null ?
                PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> userMapper.selectList(id, status, username, name, createdTime0, createdTime1)) :
                userMapper.selectList(id, status, username, name, createdTime0, createdTime1);
    }

    @DS("slave")
    @Override
    public List<Role> findRolesOfUser(long userId, Role.Type type) {
        List<Role> roles = new ArrayList<>();
        if(type == null || type == Role.Type.USER) {
            User user = getUser(userId);
            if(user != null) roles.add(user);
        }
        if(type == null || type == Role.Type.ROLE) {
            roles.addAll(userMapper.findRoles(userId));
        }
        return roles;
    }

    @DS("slave")
    @Override
    public List<String> findRoleNamesOfUser(long userId, Role.Type type) {
        return findRolesOfUser(userId, type).stream().map(role -> role.getName()).collect(Collectors.toList());
    }

    @DS("slave")
    @Override
    public List<String> findPermissionsOfRole(long roleId) {
        return roleMapper.findPermissions(roleId);
    }

    @DS("slave")
    @Override
    public List<String> findPermissionsOfUser(long userId) {
        List<Long> roleIds = new ArrayList<>();
        roleIds.add(userId);
        roleIds.addAll(userMapper.findRoleIds(userId));
        return roleMapper.findPermissions2(roleIds);
    }

    @Override
    public boolean updateRole(Role role) {
        return roleMapper.updateById(role) == 1;
    }

    @Override
    public boolean updateUser(User user) {
        String password = user.getPassword();
        if(StringUtils.hasText(password)) user.setPassword(passwordEncoder.encode(password));
        boolean ok = userMapper.updateById(user) == 1;
        user.setPassword(password);
        return ok;
    }

    @Transactional
    @Override
    public boolean setUserRoles(long userId, long... roleIds) {
        boolean ok = false;
        for(long roleId : roleIds) ok |= userMapper.setRole(userId, roleId) == 1;
        return ok;
    }

    @Transactional
    @Override
    public boolean unsetUserRoles(long userId, long... roleIds) {
        if(roleIds.length == 0) return userMapper.unsetAllRoles(userId) > 0;
        boolean ok = false;
        for(long roleId : roleIds) ok |= userMapper.unsetRole(userId, roleId) == 1;
        return ok;
    }

    @Transactional
    @Override
    public boolean setRolePermissions(long roleId, String... permissions) {
        boolean ok = false;
        for(String permission : permissions) ok |= roleMapper.setPermission(roleId, permission) == 1;
        return ok;
    }

    @Transactional
    @Override
    public boolean unsetRolePermissions(long roleId, String... permissions) {
        if(permissions.length == 0) return roleMapper.unsetAllPermissions(roleId) > 0;
        boolean ok = false;
        for(String permission : permissions) ok |= roleMapper.unsetPermission(roleId, permission) == 1;
        return ok;
    }

    @Transactional
    @Override
    public boolean deleteRole(long id) {
        roleMapper.unsetAllPermissions(id);
        return roleMapper.deleteById(id) == 1;
    }

    @Transactional
    @Override
    public boolean deleteUser(long id) {
        deleteRole(id);
        userMapper.unsetAllRoles(id);
        return userMapper.deleteById(id) == 1;
    }

}
