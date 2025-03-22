package kafka.springkafka.demo;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author: wanghaoran1
 * @create: 2025-02-27
 */
@Service
public class Consumer1 {


    @KafkaListener(topics = "test-tran", groupId = "xiao")
    public void consume(String message) {
        System.out.println("consumer1:" + message);
    }
}
