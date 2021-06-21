package springboard.example.service.impl;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;
import springboard.example.bean.Account;
import springboard.example.bean.Role;
import springboard.example.bean.User;
import springboard.example.service.AdminService;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class  AdminServiceTest {

    @Autowired
    AdminService adminService;

    //@Test
    public void populate() {
        Role role = new Role();
        role.setName("<admin>");
        role.setCreatedTime(new Date());
        role = adminService.createRole(role);

        Role role1 = new Role();
        role1.setName("<troppers_op>");
        role1.setCreatedTime(new Date());
        role1 = adminService.createRole(role1);
        adminService.grantPermissions(role1.getId(),  "troopers:read", "troopers:create", "troopers:update", "troopers:delete");

        Account account = new Account();
        account.setStatus(Account.Status.ACTIVE);
        account.setUsername("ching");
        account.setPassword("Passw0rd");
        account.setCreatedTime(new Date());
        User user = new User();
        user.setName("Ching the nameless");
        user.setCreatedTime(user.getCreatedTime());
        user.setAccount(account);
        user = adminService.createUser(user);
        adminService.setUserRoles(user.getId(), role.getId(), role1.getId());
        adminService.grantPermissions(user.getId(), "be-handsome");
    }

    @Test
    public void test1() {
        Role role = adminService.getRole(1L);
        System.out.println(role);
    }

    @Test
    public void test2() {
        User user = adminService.findUser("ching");
        System.out.println(user);
    }

    @Test
    public void test3() {
        try {
            User user = adminService.findUser("ching", "Passw0rd");
            System.out.println(user);
        } catch(BadCredentialsException x) {
            x.printStackTrace();
        }
    }

    @Test
    public void test4() {
        Page<Role> roles = adminService.listRoles(null, null, null, null,null);
        System.out.println(roles);
    }

    @Test
    public void test5() {
        Page<User> users = adminService.listUsers(null, Account.Status.ACTIVE, null,null, null,null, null, 1);
        System.out.println(users);
    }

    @Test
    public void test6() {
        adminService.updateUserAccount(3L, null, "Passw0rd",null);
        test3();
    }

    @Test
    public void test7() {
        List<String> permissions = adminService.findUserPermissions(3L);
        System.out.println(permissions);
    }

    @Test
    public void test9() {
        System.out.println(adminService.findUserRoles(3L));
        adminService.setUserRoles(3L, 1L);
        System.out.println(adminService.findUserRoles(3L));
        adminService.unsetUserRoles(3L, 1L);
        System.out.println(adminService.findUserRoles(3L));
    }

    @Transactional
    public boolean someTransaction() {
        adminService.touchUserAccount(3L, new Date(), null);
        if(RandomUtils.nextInt() % 2 == 1) throw new RuntimeException("odd fate");
        adminService.touchUserAccount(3L, null, "127.0.0.1");
        return true;
    }

    @Test
    public void test10() throws InterruptedException {
        someTransaction();
        Thread.sleep(100L);
    }

}
