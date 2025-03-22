package sa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa.service.Producer;

import javax.annotation.Resource;

/**
 * @author: 小手WA凉
 * @create: 2024-06-06
 */
@RestController
@RequestMapping("/rabbitmq")
public class DemoController {

    @Resource
    private Producer producer;

    @GetMapping("/send")
    public String send(String exchange, String routingKey) {
        producer.sendMessage(exchange, routingKey);
        return "发送消息成功";
    }
}
