package spring.ioc;

import spring.ioc.service.DemoService;

import java.io.IOException;

/**
 * @author: 小手WA凉
 * @create: 2024-09-06
 */
public class Test {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        MyAnnotationApplicationContext applicationContext = new MyAnnotationApplicationContext("spring.ioc");
        DemoService demoService = (DemoService) applicationContext.getBean(DemoService.class);
        demoService.test();
    }
}
