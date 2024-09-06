package spring.ioc.service.Impl;

import spring.ioc.annotation.Bean;
import spring.ioc.service.DemoService;

/**
 * @author: 小手WA凉
 * @create: 2024-09-06
 */
@Bean
public class DemoServiceImpl implements DemoService {
    @Override
    public void test() {
        System.out.println("test");
    }
}
