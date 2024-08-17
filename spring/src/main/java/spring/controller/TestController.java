package spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.test.Test;

import javax.annotation.Resource;

/**
 * @author: 小手WA凉
 * @create: 2024-07-05
 */
@RestController
public class TestController {
    @Resource
    private Test test;
    @GetMapping("/test")
    public String test(){
        test.update();
        return "666";
    }
}
