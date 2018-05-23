package springboard.example.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springboard.example.model.AdminService;
import springboard.example.model.Role;
import springboard.example.model.User;
import springboard.example.service.ServiceApplication;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestAdminServiceImpl {

    @Autowired
    AdminService adminService;

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
        User user = adminService.getUser(1000);
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
    public void testUpdateUser() {
        User user = new User();
        user.setId(1000L);
        user.setPassword("Passw0rd");
        boolean ok = adminService.updateUser(user);
        System.out.println(ok);
    }

}
