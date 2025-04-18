package other.juc.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池
 *
 * @author: wanghaoran1
 * @create: 2025-01-10
 */
public class MyThreadPool implements Executor {

    /**
     * 线程集合
     */
    public HashSet<Thread> threads = new HashSet<>();
    Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 任务队列
     */
    private MyBlockingQueue<Runnable> taskQueue;
    /**
     * 核心线程数
     */
    private int coreSize;

    /**
     * 拒绝策略
     */
    private RejectPolicy<Runnable> rejectPolicy;

    public MyThreadPool(int coreSize, MyBlockingQueue<Runnable> taskQueue, RejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.taskQueue = taskQueue;
        this.rejectPolicy = rejectPolicy;
    }

    @Override
    public void execute(Runnable command) {
        synchronized (threads) {
            if (threads.size() < coreSize) {
                Worker worker = new Worker(command);
                worker.start();
                threads.add(worker);
            } else {
                taskQueue.tryPut(command, rejectPolicy);
            }
        }
    }

    class Worker extends Thread {

        private Runnable firstTask;

        public Worker(Runnable firstTask) {
            this.firstTask = firstTask;
        }

        @Override
        public void run() {
            Runnable task = firstTask;
            //如果这里换成take阻塞获取，则没任务时也会卡在while这里，线程池不会关闭
            while (task != null || (task = taskQueue.poll(500L, TimeUnit.MILLISECONDS)) != null) {
                task.run();
                task = null;
            }
            synchronized (threads) {
                logger.info("remove线程");
                threads.remove(this);
            }
        }
    }
}
