package other.juc.pattern;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 保护性暂停模式
 @author: wanghaoran1
 @create: 2025-04-18
 */
@Slf4j
public class Demo1 {
    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject();
        Thread thread1 = new Thread(() -> {
            Object response = guardedObject.get(2000);
            log.info("收到结果：{}", response);
        }, "t1");
        Thread thread2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                guardedObject.set(new Object());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t2");
        log.info("开始");
        thread1.start();
        thread2.start();
    }

}

class GuardedObject {
    Object response;

    public Object get(long millisecond) {
        synchronized (this) {
            long startTime = System.currentTimeMillis();
            long passTime = 0;//已经等待的时间
            while (response == null) {
                try {
                    if (passTime >= millisecond) {
                        break;
                    }
                    this.wait(millisecond - passTime);
                    passTime = System.currentTimeMillis() - startTime;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return response;
    }

    public void set(Object response) {
        synchronized (this) {
            this.response = response;
            this.notifyAll();
        }
    }
}
