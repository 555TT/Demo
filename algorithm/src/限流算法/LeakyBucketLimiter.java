package 限流算法;

/**
 * @author: 小手WA凉
 * @create: 2024-07-13
 */
public class LeakyBucketLimiter {
    private long timeStamp = System.currentTimeMillis();
    private long capacity = 100;//桶的容量
    private long rate = 100;//水漏出的速率（系统每秒处理请求的速率）
    private long water = 20;//当前水量（当前累计请求数）

    /**
     * 返回true代表限流，false代表通过
     *
     * @return
     */
    public synchronized boolean limit() {
        long now = System.currentTimeMillis();
        water = Math.max(0, water - (now - timeStamp) / 1000 * rate);
        timeStamp = now;
        if (water + 1 <= capacity) {
            //水未满，加水
            water++;
            return false;
        } else {
            //水满，拒绝加水，被限流
            return true;
        }
    }
}
