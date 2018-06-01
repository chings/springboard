package springboard.example.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springboard.example.model.AdminService;
import springboard.example.model.User;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AdminController {

    private static Logger log = LoggerFactory.getLogger(AdminController.class);

    @Reference
    AdminService adminService;

    @PostMapping("/login")
    public Object login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
        } catch(AuthenticationException x) {
            log.warn("{} was thrown", x.getClass(), x);
            throw x;
        }
        return "OK";
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Object logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "";
    }

    @PostMapping("/users/{id}/password")
    public Object resetUserPassword(@PathVariable("id") long id, @RequestParam("password") String password) {
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        return adminService.updateUser(user);
    }

    @GetMapping("/users/{id}/permissions")
    public Object getUserPermissions(@PathVariable("id") long id) {
        return adminService.findPermissionsOfUser(id);
    }

    @GetMapping("/principal")
    public Object peekPrincipal(Subject subject) {
        return subject.getPrincipal();
    }

    @GetMapping(value = "/session")
    public Object peekSession(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        for(Enumeration<String> e = session.getAttributeNames(); e.hasMoreElements(); ) {
            String attributeName = e.nextElement();
            result.put(attributeName, session.getAttribute(attributeName));
        }
        return result;
    }

}
