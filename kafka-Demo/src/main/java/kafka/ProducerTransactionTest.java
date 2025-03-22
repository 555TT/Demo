package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

public class ProducerTransactionTest {
    public static void main(String[] args) {
        Map<String, Object> configMap = new HashMap<>();
        configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.200.130:9092");
        configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //  配置幂等性
        configMap.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        configMap.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5);
        configMap.put(ProducerConfig.ACKS_CONFIG, "-1");
        configMap.put(ProducerConfig.RETRIES_CONFIG, 5);
        //  配置事务ID
        configMap.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "my-tx-id");
        //  配置事务超时时间
        configMap.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, 5000);
        //  创建生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<>(configMap);
        //  初始化事务
        producer.initTransactions();
        try {
            //  启动事务
            producer.beginTransaction();
            //  生产数据
            for (int i = 0; i < 10; i++) {
                ProducerRecord<String, String> record = new ProducerRecord<String, String>("test-tran", "herkey" + i, "hervalue" + i);
                final Future<RecordMetadata> send = producer.send(record);
            }
            //  提交事务
            producer.commitTransaction();
            int i = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            //  终止事务
            producer.abortTransaction();
            System.out.println("终止事务1");
        } finally {
            // 关闭生产者对象
            System.out.println("关闭生产者");
            producer.close();
        }


    }
}