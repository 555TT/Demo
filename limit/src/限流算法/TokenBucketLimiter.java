package 限流算法;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: 小手WA凉
 * @create: 2024-07-13
 */
public class TokenBucketLimiter {
    private final int capacity=100;//令牌桶的容量
    private final int rate=5;//令牌生成速率
    private volatile AtomicInteger tokens=new AtomicInteger(0);//桶中令牌数量

    /**
     * 开启一个线程固定频率往桶中放入令牌
     */
    private void productToken(){
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(()->{
            tokens.set(Math.min(capacity,tokens.get()+rate));
        },0,1, TimeUnit.SECONDS);//一秒执行一次
    }
    private boolean isLimited(int requestCount){
        if(tokens.get()<requestCount)
            return true;//被限流
        else {
            tokens.getAndAdd(-requestCount);
            return false;
        }
    }
}
