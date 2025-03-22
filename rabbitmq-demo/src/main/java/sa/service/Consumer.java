package sa.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author: 小手WA凉
 * @create: 2024-06-06
 */
@Slf4j
@Component
public class Consumer {
    @RabbitListener(queues = "liked_queue")
    public void handlerMessage(Message message, String dataString, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        Boolean redelivered = message.getMessageProperties().isRedelivered();
        try {
            log.info("消费消息：{}", dataString);
            channel.basicAck(deliveryTag, false);
            log.info("成功消费消息");
        } catch (Exception e) {
            log.info("消费消息失败");
            if (redelivered) {
                channel.basicNack(deliveryTag, false, false);
            } else {
                channel.basicNack(deliveryTag, false, true);
            }
        }
    }
}
