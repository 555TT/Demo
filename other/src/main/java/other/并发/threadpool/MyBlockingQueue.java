package other.并发.threadpool;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义任务队列
 *
 * @author: wanghaoran1
 * @create: 2025-01-10
 */
public class MyBlockingQueue<T> {

    /**
     * 任务队列
     */
    private final Deque<T> deque = new ArrayDeque<>();
    /**
     * 锁
     */
    private final ReentrantLock lock = new ReentrantLock();
    /**
     * 最大容量
     */
    private int capacity;
    /**
     * 生产者条件变量
     */
    private Condition fullWaitSet = lock.newCondition();


    /**
     * 消费者条件变量
     */
    private Condition emptyWaitSet = lock.newCondition();

    public MyBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public MyBlockingQueue() {
        this(Integer.MAX_VALUE);
    }

    /**
     * 阻塞方式从队列中获取任务
     *
     * @return
     */
    public T take() {
        T task;
        lock.lock();
        try {
            while (deque.isEmpty()) {
                try {
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            task = deque.removeFirst();
            fullWaitSet.signalAll();
            return task;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 阻塞添加任务
     *
     * @param task
     * @return
     */
    public void put(T task) {
        lock.lock();
        try {
            while (deque.size() == capacity) {
                fullWaitSet.await();
            }
            deque.offerLast(task);
            emptyWaitSet.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 带超时时间的阻塞获取
     *
     * @param timeout
     * @param unit
     * @return
     */
    public T poll(long timeout, TimeUnit unit) {
        lock.lock();
        long nanos = unit.toNanos(timeout);
        try {
            while (deque.isEmpty()) {
                if (nanos <= 0) {
                    return null;
                }
                nanos = emptyWaitSet.awaitNanos(nanos);
            }
            T task = deque.removeFirst();
            fullWaitSet.signalAll();
            return task;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 带超时时间的阻塞添加
     *
     * @param task    任务
     * @param timeout 超时时间
     * @param unit    事件单位
     */
    public boolean offer(T task, long timeout, TimeUnit unit) {
        lock.lock();
        long nanos = unit.toNanos(timeout);
        try {
            while (deque.size() == capacity) {
                if (nanos <= 0) {
                    return false;
                }
                nanos = fullWaitSet.awaitNanos(nanos);
            }
            deque.addFirst(task);
            emptyWaitSet.signalAll();
            return true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取任务队列的任务数量
     *
     * @return
     */
    public int size() {
        try {
            lock.lock();
            return deque.size();
        } finally {
            lock.unlock();
        }
    }

    public void tryPut(T task, RejectPolicy<T> rejectPolicy) {
        lock.lock();
        try {
            if (deque.size() == capacity) {
                rejectPolicy.reject(this, task);
            } else {
                deque.addLast(task);
                emptyWaitSet.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
}
