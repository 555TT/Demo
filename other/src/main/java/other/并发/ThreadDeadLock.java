package other.并发;

/**
 * @author: 小手WA凉
 * @create: 2024-10-09
 */

/**
 * 死锁
 */
public class ThreadDeadLock {
    final static Object lock1 = new Object();
    final static Object lock2 = new Object();
    public static void deadLock() {
        new Thread(() -> {
            synchronized (lock1) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lock2) {

                }
            }
        }).start();
        new Thread(() -> {
            synchronized (lock2) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lock1) {

                }
            }
        }).start();
    }

    public static void main(String[] args) {
        ThreadDeadLock.deadLock();
    }
}
