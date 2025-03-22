package sa.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import sa.entity.Liked;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author: 小手WA凉
 * @create: 2024-06-06
 */
@Service
@Slf4j
public class Producer {
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String exchange, String routingKey) {
        Liked liked = new Liked();
        liked.setId(1);
        liked.setStatus(1);
        liked.setSubjectId(1);
        liked.setUserName("jack");
        log.info("生产者发送消息：{}", liked);
        Message message = MessageBuilder.
                withBody(JSON.toJSONString(liked).getBytes(StandardCharsets.UTF_8)).
                setDeliveryMode(MessageDeliveryMode.PERSISTENT).
                build();//消息持久化
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
