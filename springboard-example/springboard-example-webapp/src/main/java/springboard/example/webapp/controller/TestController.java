package springboard.example.webapp.controller;

import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springboard.web.exception.NotFoundException;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    @ResponseBody Object home() {
        Map<String, Object> result = new HashMap<>();
        result.put("ok", true);
        result.put("code", 200);
        result.put("message", "Hello World!");
        result.put("time", new Date());
        return result;
    }

    @GetMapping("/404")
    @ResponseBody Object error() {
        throw new NotFoundException("Sorry, it's gone.");
    }

    @GetMapping("/principal")
    public Object principal(Subject subject) {
        return subject.getPrincipal();
    }

    @GetMapping("/session")
    public Object session(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        for(Enumeration<String> e = session.getAttributeNames(); e.hasMoreElements(); ) {
            String attributeName = e.nextElement();
            result.put(attributeName, session.getAttribute(attributeName));
        }
        return result;
    }

}
