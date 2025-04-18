package other.juc.pattern;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * 生产者消费者模式
 @author: wanghaoran1
 @create: 2025-04-18
 */
@Slf4j
public class Demo2 {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(5);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 6; i++) {
                messageQueue.put(new Message(i, new Object()));
            }

        }, "t1");
        Thread t2 = new Thread(() -> {
            while (true) {
                log.info("消费消息：{}", messageQueue.take());
            }
        }, "t2");
        t1.start();
        t2.start();
    }
}

class MessageQueue {
    private LinkedList<Message> queue = new LinkedList<>();
    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * 阻塞获取
     * @return
     */
    public Message take() {
        synchronized (queue) {
            while (queue.isEmpty()) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Message message = queue.removeFirst();
            queue.notifyAll();
            return message;
        }
    }

    public void put(Message message) {
        synchronized (queue) {
            while (queue.size() == capacity) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            queue.offerLast(message);
            queue.notifyAll();
        }
    }

}

class Message {
    private int id;
    private Object val;

    public Message(int id, Object val) {
        this.id = id;
        this.val = val;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", val=" + val +
                '}';
    }
}
