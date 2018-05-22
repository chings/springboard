package springboard.example.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springboard.web.exception.NotFoundException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {

    @GetMapping({"/", "/hello"})
    @ResponseBody Object home() {
        Map<String, Object> result = new HashMap<>();
        result.put("ok", true);
        result.put("code", 200);
        result.put("message", "Hello World!");
        result.put("time", new Date());
        return result;
    }

    @GetMapping({"/404"})
    @ResponseBody Object error() {
        throw new NotFoundException("Sorry, it's gone.");
    }

}
