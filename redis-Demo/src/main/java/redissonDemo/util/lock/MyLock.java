package redissonDemo.util.lock;

import java.util.concurrent.locks.Lock;

/**
 * @author: 小手WA凉
 * @create: 2024-07-10
 */
public interface MyLock extends Lock {
    String getType();
}
