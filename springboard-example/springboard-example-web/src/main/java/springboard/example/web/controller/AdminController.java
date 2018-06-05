package springboard.example.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springboard.example.model.AdminService;
import springboard.example.model.LoggedInEvent;
import springboard.example.model.User;
import springboard.lang.EventPublisher;
import springboard.web.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class AdminController {

    private static Logger log = LoggerFactory.getLogger(AdminController.class);

    @Reference
    AdminService adminService;

    @Autowired
    EventPublisher eventPublisher;

    @PostMapping("/login")
    public Object login(HttpServletRequest request,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
        } catch(AuthenticationException x) {
            log.warn("{} was thrown", x.getClass(), x);
            throw new UnauthorizedException("The username or password was not correct.");
        }

        User user = adminService.getUser(username);
        LoggedInEvent loggedInEvent = new LoggedInEvent();
        loggedInEvent.setUserId(user.getId());
        loggedInEvent.setUsername(username);
        loggedInEvent.setLoggedInTime(new Date());
        loggedInEvent.setLoggedInAddr(request.getRemoteAddr());
        eventPublisher.publish(loggedInEvent);

        return "OK";
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Object logout() {
        SecurityUtils.getSubject().logout();
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

    @EventListener(LoggedInEvent.class)
    public void handleLoggedIn(LoggedInEvent event) {
        User user = new User();
        user.setId(event.getUserId());
        user.setLastLoggedInTime(event.getLoggedInTime());
        user.setLastLoggedInAddr(event.getLoggedInAddr());
        adminService.updateUser(user);
    }

}
