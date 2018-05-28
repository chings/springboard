package springboard.example.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springboard.example.model.AdminService;
import springboard.example.model.Role;
import springboard.example.model.User;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestAdminServiceImpl {

    @Autowired
    AdminService adminService;

    @Test
    public void testCreateRole() {
        Role role = new Role();
        role.setName("<troppers_op>");
        role.setCreatedTime(new Date());
        role = adminService.createRole(role);
        System.out.println(role);
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("ching");
        user.setPassword("the nameless");
        user.setName("Ching");
        user.setCreatedTime(new Date());
        user = adminService.createUser(user);
        System.out.println(user);
    }

    @Test
    public void testGetRole() {
        Role role = adminService.getRole(1);
        System.out.println(role);
    }

    @Test
    public void testGetUser() {
        User user = adminService.getUser(3);
        System.out.println(user);
    }

    @Test
    public void testGetUser2() {
        User user = adminService.getUser("admin", "Password");
        System.out.println(user);
    }

    @Test
    public void testFindRoles() {
        List<Role> roles = adminService.findRoles(null, null, null, null,null,1, 2);
        System.out.println(roles);
    }

    @Test
    public void testFindUsers() {
        List<User> users = adminService.findUsers(null, null, null, null,null,1, 2);
        System.out.println(users);
    }

    @Test
    public void testFindRolesOfUser() {
        List<Role> roles = adminService.findRolesOfUser(3L);
        System.out.println(roles);
    }

    @Test
    public void testFindPermissionsOfUser() {
        List<String> permissions = adminService.findPermissionsOfUser(3L);
        System.out.println(permissions);
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(1000L);
        user.setPassword("Passw0rd");
        adminService.updateUser(user);
    }

    @Test
    public void testSetUserRoles() {
        adminService.setUserRoles(3, 1);
    }

    @Test
    public void testUnsetUserRoles() {
        adminService.unsetUserRoles(3);
    }

    @Test
    public void testSetRolePermissions() {
        adminService.setRolePermissions(4,  "troopers:read", "troopers:create", "troopers:update", "troopers:delete");
    }

}
