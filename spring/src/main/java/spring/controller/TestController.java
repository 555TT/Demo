package spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.service.TranTest;
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


    @Resource
    private TranTest tranTest;

    @GetMapping("/test")
    public String test() {
        test.update();
        return "666";
    }


    /**
     * 无事务方法调用事务方法，无事务方法里异常
     * 预期：都不会回滚 √
     * 无事务方法调用事务方法，事务方法里异常。结果：事务方法里会回滚
     *
     * @return
     */
    @GetMapping("/Trantest1")
    public String Trantest() {
        tranTest.fun2();
        return "666";
    }

    /**
     * 事务方法里调用无事务方法，无事务方法里有异常
     * 预期：事务方法里会回滚，无事务方法里不会回滚
     *
     * @return
     */
    @GetMapping("/Trantest2")
    public String Trantest2() {
        tranTest.fun1();
        return "666";
    }
}
