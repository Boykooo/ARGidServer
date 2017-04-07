package rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest")
public class TestController {

    @RequestMapping("/test")
    public String test(){
        return "hello";
    }
}
