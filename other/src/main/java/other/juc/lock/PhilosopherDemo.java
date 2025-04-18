package other.juc.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 哲学家就餐问题
 @author: wanghaoran1
 @create: 2025-04-18
 */
public class PhilosopherDemo {
    public static void main(String[] args) {
        Chopsticks chopstick1 = new Chopsticks(1);
        Chopsticks chopstick2 = new Chopsticks(2);
        Chopsticks chopstick3 = new Chopsticks(3);
        Chopsticks chopstick4 = new Chopsticks(4);
        Chopsticks chopstick5 = new Chopsticks(5);
        new Philosopher("苏格拉底", chopstick1, chopstick2).start();
        new Philosopher("亚里士多德", chopstick2, chopstick3).start();
        new Philosopher("阿基米德", chopstick3, chopstick4).start();
        new Philosopher("柏拉图", chopstick4, chopstick5).start();
        new Philosopher("赫拉克利特", chopstick5, chopstick1).start();
    }
}

@Slf4j
class Philosopher extends Thread {
    private Chopsticks left;
    private Chopsticks right;

    public Philosopher(String name, Chopsticks chopstick1, Chopsticks chopstick2) {
        super(name);
        this.left = chopstick1;
        this.right = chopstick2;
    }

    @Override
    public void run() {
        while (true) {
            //这里解决死锁，可以选用带超时时间的锁，比如ReentrantLock的tryLock
            synchronized (left) {
                synchronized (right) {
                    eating();
                }
            }
        }
    }

    private void eating() {
        log.info("{}正在吃", Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Chopsticks {
    int id;

    public Chopsticks(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Chopsticks{" +
                "id=" + id +
                '}';
    }
}
