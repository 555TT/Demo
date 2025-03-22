package redissonDemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redissonDemo.service.impl.DistributedLockService;

import javax.annotation.Resource;

/**
 * @author: 小手WA凉
 * @create: 2024-07-10
 */
@RestController
public class DistributedLockController {
    @Resource
    private DistributedLockService service;

    @GetMapping("/inventory/sale")
    public void sale() {
        service.sale();
    }
}
