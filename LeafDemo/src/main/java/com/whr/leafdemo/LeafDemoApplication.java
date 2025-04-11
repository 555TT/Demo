package com.whr.leafdemo;

import com.sankuai.inf.leaf.plugin.annotation.EnableLeafServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableLeafServer
public class LeafDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeafDemoApplication.class, args);
    }

}
