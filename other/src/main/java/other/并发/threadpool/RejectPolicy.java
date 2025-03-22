package other.并发.threadpool;

/**
 * 拒绝策略
 *
 * @author: wanghaoran1
 * @create: 2025-01-10
 */
@FunctionalInterface
public interface RejectPolicy<T> {
    void reject(MyBlockingQueue<T> queue, T task);
}
