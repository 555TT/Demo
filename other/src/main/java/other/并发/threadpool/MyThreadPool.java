package other.并发.threadpool;

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
     * 任务队列
     */
    private MyBlockingQueue<Runnable> taskQueue;

    /**
     * 线程集合
     */
    private HashSet<Thread> threads = new HashSet<>();

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
        synchronized (threads){
            if(threads.size()<coreSize){
                Worker worker = new Worker(command);
                worker.start();
                threads.add(worker);
            }else if(taskQueue.offer(command,500L, TimeUnit.MILLISECONDS)){
                rejectPolicy.reject(taskQueue,command);
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
            while (task != null || (task = taskQueue.take()) != null) {
                task.run();
                task = null;
            }
            synchronized (threads) {
                threads.remove(this);
            }
        }
    }
}
