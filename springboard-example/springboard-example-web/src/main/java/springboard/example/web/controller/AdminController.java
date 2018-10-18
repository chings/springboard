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
import springboard.annotation.Idempotent;
import springboard.example.model.AdminService;
import springboard.example.model.LoggedInEvent;
import springboard.example.model.User;
import springboard.lang.EventPublisher;
import springboard.web.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController("/admin")
public class AdminController {

    private static Logger log = LoggerFactory.getLogger(AdminController.class);

    @Reference
    AdminService adminService;

    @GetMapping("users")
    public Object users(@RequestParam(name = "id", required = false) Long id,
                        @RequestParam(name = "status", required = false) User.Status status,
                        @RequestParam(name = "username", required = false) String username,
                        @RequestParam(name = "name", required = false) String name,
                        @RequestParam(name = "createdTime0", required = false) Date createdTime0,
                        @RequestParam(name = "createdTime1", required = false) Date createdTime1,
                        @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
                        @RequestParam(name = "pageSize", defaultValue = "20") int pageSize) {
        return adminService.findUsers(id, status, username, name, createdTime0, createdTime1, pageNum, pageSize);
    }

}
