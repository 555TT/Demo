package other.并发.threadpool;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: wanghaoran1
 * @create: 2025-01-10
 */
public class Main {
    public static void main(String[] args) {
        MyBlockingQueue<Runnable> queue = new MyBlockingQueue<>(5);
        MyThreadPool myThreadPool = new MyThreadPool(5, queue, (queue1, task) -> {
            System.out.println("拒绝。。。。。。。。。");
        });
        for (int i = 0; i < 10; i++) {
            int j=i;
            myThreadPool.execute(()->{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("执行"+j);

            });
        }
    }
}
