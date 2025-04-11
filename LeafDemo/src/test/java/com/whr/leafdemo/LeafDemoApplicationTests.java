package com.whr.leafdemo;

import com.sankuai.inf.leaf.service.SegmentService;
import com.sankuai.inf.leaf.service.SnowflakeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class LeafDemoApplicationTests {

    @Resource
    private SegmentService segmentService;

    @Resource
    private SnowflakeService snowflakeService;

    @Test
    public void test1() {
        // 生成 1000 个ID
        for (int i = 0; i < 1000; i++) {
            long id1 = segmentService.getId("test1").getId();
            long id2 = segmentService.getId("test2").getId();
            System.out.println("id1:" + id1);
            System.out.println("id2:" + id2);
        }
    }

    @Test
    public void test2() {
        // 生成 1000 个ID
        for (int i = 0; i < 1000; i++) {
            long id1 = snowflakeService.getId("test1").getId();
            long id2 = snowflakeService.getId("test2").getId();
            System.out.println("id1:" + id1);
            System.out.println("id2:" + id2);
        }
    }
}
