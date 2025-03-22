package sa.cofig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: 小手WA凉
 * @create: 2024-06-06
 */
@Slf4j
@Component
public class ConfirmConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback, InitializingBean {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String s) {
        if (ack) {
            log.info("消息投递到交换机上成功");
        } else {
            log.info("消息投递上交换机上失败，原因：{}", s);
        }
    }

    //只有投递到队列上失败才会调用此方法
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.info("消息分发到队列上失败");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);

    }
}
