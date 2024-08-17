package spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: 小手WA凉
 * @create: 2024-07-05
 */
@SpringBootApplication
@MapperScan("spring.dao")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
