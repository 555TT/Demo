package kafka.springkafka.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: wanghaoran1
 * @create: 2025-03-18
 */
@Service
@Slf4j
public class Producer1 {

    @Resource
    private KafkaProducer<String, String> kafkaProducer;

    @Resource
    private KafkaTemplate<String,String> kafkaTemplate;


    public void send() {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("test", "异步消息");
        kafkaProducer.send(producerRecord, (metadata, exception) -> {
            log.info("消息回调");
        });
    }
}
