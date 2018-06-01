package springboard.example.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import springboard.example.dao.RoleMapper;
import springboard.example.dao.UserMapper;
import springboard.example.model.AdminService;
import springboard.example.model.Role;
import springboard.example.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@com.alibaba.dubbo.config.annotation.Service
public class AdminServiceImpl implements AdminService {

    private static Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

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

    @Override
    public Role getRole(long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public User getUser(long id) {
        return userMapper.selectById(id);
    }

    @Override
    public User getUser(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public User getUser(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if(user == null) return null;
        if(!passwordEncoder.matches(password, user.getPassword())) return null;
        return user;
    }

    @Override
    public List<Role> findRoles(Long id, Role.Type type, String name, Date createdTime0, Date createdTime1, Integer... pagination) {
        Wrapper<Role> criteria = new EntityWrapper<>();
        if(id != null) criteria.eq("id", id);
        if(type != null) criteria.eq("type", type);
        if(name != null) criteria.like("name", name);
        if(createdTime0 != null) criteria.ge("created_time", createdTime0);
        if(createdTime1 != null) criteria.lt("created_time", createdTime1);
        Integer pageNum = pagination.length > 0 ? pagination[0]: null;
        Integer pageSize = pagination.length > 1 ? pagination[1] : DEFAULT_PAGE_SIZE;
        return pagination != null ?
                PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> roleMapper.selectList(criteria)) :
                roleMapper.selectList(criteria);
    }

    @Override
    public List<User> findUsers(Long id, User.Status status, String username, String name, Date createdTime0, Date createdTime1, Integer... pagination) {
        Integer pageNum = pagination.length > 0 ? pagination[0]: null;
        Integer pageSize = pagination.length > 1 ? pagination[1] : DEFAULT_PAGE_SIZE;
        return pagination != null ?
                PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> userMapper.selectList(id, status, username, name, createdTime0, createdTime1)) :
                userMapper.selectList(id, status, username, name, createdTime0, createdTime1);
    }

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

    @Override
    public List<String> findRoleNamesOfUser(long userId, Role.Type type) {
        return findRolesOfUser(userId, type).stream().map(role -> role.getName()).collect(Collectors.toList());
    }

    @Override
    public List<String> findPermissionsOfRole(long roleId) {
        return roleMapper.findPermissions(roleId);
    }

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
