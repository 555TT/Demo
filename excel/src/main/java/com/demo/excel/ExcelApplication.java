package com.demo.excel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: 小手WA凉
 * @create: 2024-08-16
 */
@SpringBootApplication
@MapperScan("com.demo.excel.dao")
public class ExcelApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExcelApplication.class);
    }
}
