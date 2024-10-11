package other.并发.交替打印;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: 小手WA凉
 * @create: 2024-08-21
 */
public class ReentrantLockPrint {
    static final ReentrantLock lock = new ReentrantLock();
    static final Condition condition1 = lock.newCondition();
    static final Condition condition2 = lock.newCondition();
    static boolean flag = true;//true 打印1 false 打印2

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        lock.lock();
                        if (!flag)
                            condition1.await();
                        System.out.print("1 ");
                        flag = false;
                        condition2.signal();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        lock.unlock();
                    }
                }

            }
        }
        );
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        lock.lock();
                        if (flag)
                            condition2.await();
                        System.out.print("2 ");
                        flag = true;
                        condition1.signal();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
        );
        thread1.start();
        thread2.start();
    }
}
