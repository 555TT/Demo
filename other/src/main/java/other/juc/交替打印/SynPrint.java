package other.juc.交替打印;

/**
 * @author: 小手WA凉
 * @create: 2024-08-21
 */
public class SynPrint {

    static final Object lock = new Object();
    static int state = 1;

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                synchronized (lock) {
                    try {
                        if (state % 2 != 0)
                            lock.wait();
                        System.out.print("1 ");
                        state++;
                        lock.notify();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                synchronized (lock) {
                    try {
                        if (state % 2 == 0)
                            lock.wait();
                        System.out.print("2 ");
                        state++;
                        lock.notify();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread2.start();
        thread1.start();
    }
}
