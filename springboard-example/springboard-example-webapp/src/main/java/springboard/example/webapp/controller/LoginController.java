package springboard.example.webapp.controller;

import org.apache.dubbo.config.annotation.DubboReference;
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
import springboard.example.bean.User;
import springboard.example.event.LoggedInEvent;
import springboard.example.service.AdminService;
import springboard.lang.EventPublisher;
import springboard.lang.annotation.Idempotent;
import springboard.web.exception.NotFoundException;
import springboard.web.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired(required = false) //compatible without Dubbo RPC support
    @DubboReference //compatible with Dubbo RPC support
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

        User user = adminService.findUser(username);
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
        return adminService.updateUserAccount(id, null, password);
    }

    @GetMapping("/users/{id}/permissions")
    public Object getUserPermissions(@PathVariable("id") long id) {
        User user = adminService.getUser(id);
        if(user == null) throw new NotFoundException();
        return adminService.findUserPermissions(id);
    }

    @EventListener(LoggedInEvent.class)
    @Idempotent
    public void handleLoggedIn(LoggedInEvent event) {
        adminService.touchUserAccount(event.getUserId(), event.getLoggedInTime(), event.getLoggedInAddr());
    }

    @EventListener(LoggedInEvent.class)
    public void traceLoggedIn(LoggedInEvent event) {
        log.info(event.toString());
    }

}
