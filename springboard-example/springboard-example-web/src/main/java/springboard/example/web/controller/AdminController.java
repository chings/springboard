package springboard.example.web.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springboard.example.model.Account;
import springboard.example.model.AdminService;

import java.util.Date;

@RestController
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @DubboReference
    AdminService adminService;

    @GetMapping("/accounts")
    public Object accounts(@RequestParam(name = "id", required = false) Long id,
                        @RequestParam(name = "status", required = false) Account.Status status,
                        @RequestParam(name = "username", required = false) String username,
                        @RequestParam(name = "name", required = false) String name,
                        @RequestParam(name = "createdTime0", required = false) Date createdTime0,
                        @RequestParam(name = "createdTime1", required = false) Date createdTime1,
                        @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
                        @RequestParam(name = "pageSize", defaultValue = "20") int pageSize) {
        Page<Account> result = null;
        return result;
    }

}
