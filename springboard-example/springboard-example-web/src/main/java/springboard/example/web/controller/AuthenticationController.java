package springboard.example.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    private static Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
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

    @GetMapping("/principal")
    public Object principal(Subject subject) {
        return subject.getPrincipal();
    }

    @RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Object logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "";
    }

}
