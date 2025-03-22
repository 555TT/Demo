package other.并发.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: wanghaoran1
 * @create: 2025-01-10
 */
public class Main {
    static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        MyBlockingQueue<Runnable> queue = new MyBlockingQueue<>(5);
        MyThreadPool myThreadPool = new MyThreadPool(5, queue, (queue1, task) -> {
            log.info("拒绝策略");
        });
        for (int i = 0; i < 11; i++) {
            int j = i;
            myThreadPool.execute(() -> {
                log.info("运行：{}", j);
            });
        }
        Thread.sleep(2000L);
        log.info("线程数量:{}", myThreadPool.threads.size());

    }
}
