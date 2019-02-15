package springboard.example.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import springboard.example.model.AdminService;
import springboard.example.model.Role;
import springboard.example.model.User;

import java.util.Date;
import java.util.List;
import java.util.Random;

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
        user.setStatus(User.Status.ACTIVE);
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
        User user = adminService.getUser("admin", "Passw0rd");
        System.out.println(user);
    }

    @Test
    public void testFindRoles() {
        Page<Role> roles = adminService.findRoles(null, null, "admin", null,null);
        System.out.println(roles);
    }

    @Test
    public void testFindUsers() {
        Page<User> users = adminService.findUsers(null, User.Status.ACTIVE, null,null, null,null, 1);
        System.out.println(users);
    }

    @Test
    public void testFindRolesOfUser() {
        List<Role> roles = adminService.findRolesOfUser(3L, null);
        System.out.println(roles);
    }

    @Test
    public void testFindPermissionsOfRole() {
        List<String> permissions = adminService.findPermissionsOfRole(4L);
        System.out.println(permissions);
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

    @Transactional
    public boolean someTransaction() {
        User user = adminService.getUser(3);
        user.setLastLoggedInTime(new Date());
        user.setLastLoggedInAddr(null);
        adminService.updateUser(user);
        if(RandomUtils.nextInt() % 2 == 1) throw new RuntimeException("odd fate");
        user.setLastLoggedInAddr("127.0.0.1");
        adminService.updateUser(user);
        return true;
    }

    @Test
    public void testSomeTransaction() throws InterruptedException {
        someTransaction();
        Thread.sleep(100L);
    }

}
