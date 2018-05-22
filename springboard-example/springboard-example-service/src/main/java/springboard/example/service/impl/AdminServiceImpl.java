package springboard.example.service.impl;

import com.github.pagehelper.Page;
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

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private static Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Role create(Role role) {
        boolean ok = roleMapper.create(role) == 1;
        return ok ? role : null;
    }

    @Transactional
    @Override
    public User create(User user) {
        Role role = new Role();
        role.setType(user.getType());
        role.setName(user.getName());
        role.setCreatedTime(user.getCreatedTime());
        role = create(role);
        if(role == null) return null;

        user.setId(role.getId());
        String password = user.getPassword();
        if(!StringUtils.isEmpty(password)) user.setPassword(passwordEncoder.encode(password));
        boolean ok = userMapper.create(user) == 1;
        user.setPassword(password);
        return ok ? user : null;
    }

    @Override
    public Role getRole(long id) {
        return roleMapper.get(id);
    }

    @Override
    public User getUser(long id) {
        return userMapper.get(id);
    }

    @Override
    public User getUser(String username, String password) {
        User user = userMapper.getByUsername(username);
        if(user == null) return null;
        if(!passwordEncoder.matches(password, user.getPassword())) return null;
        return user;
    }

    @Override
    public List<Role> findRoles(Role.Type type, String name, Integer pageNum, Integer pageSize) {
        return pageNum != null && pageSize != null ?
                PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> roleMapper.find(type, name)) :
                roleMapper.find(type, name);
    }

    @Override
    public boolean update(Role role) {
        return roleMapper.update(role) == 1;
    }

    @Override
    public boolean update(User user) {
        String password = user.getPassword();
        if(!StringUtils.isEmpty(password)) user.setPassword(passwordEncoder.encode(password));
        boolean ok = userMapper.update(user) == 1;
        user.setPassword(password);
        return ok;
    }

}
