package com.xiaoshouwaliang.rocketmqdemo.demo;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.Test;

import java.util.List;

/**
 * @author: wanghaoran1
 * @create: 2025-03-05
 */
public class ATest {


    @Test
    public void testOrder() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-consumer");
        consumer.setNamesrvAddr("localhost:9200");
        consumer.subscribe("test-topic", "*");
        consumer.registerMessageListener((MessageListenerOrderly) (msgs, context) -> null);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                return null;
            }
        });
    }

}
