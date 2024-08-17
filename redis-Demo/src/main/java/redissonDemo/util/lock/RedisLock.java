package redissonDemo.util.lock;

import cn.hutool.core.util.IdUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * @author: 小手WA凉
 * @create: 2024-07-10
 */
@Component
public class RedisLock implements MyLock {
    private static String lockName="redisLock";
    @Resource
    private RedisTemplate redisTemplate;

    private Long expireTime=30L;

    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        try {
            String uuid=IdUtil.simpleUUID()+":"+Thread.currentThread().getId();
            String luaScript="if redis.call('exists',KEYS[1]) == 0 or redis.call('hexists',KEYS[1],ARGV[1]) == 1 then " +
                    "redis.call('hincrby',KEYS[1],ARGV[1],1) " +
                    "redis.call('expire',KEYS[1],ARGV[2]) " +
                    "return 1 " +
                    "else " +
                    "return 0 " +
                    "end";
            while (!(boolean) redisTemplate.execute(new DefaultRedisScript(luaScript, Boolean.class), Arrays.asList(lockName),uuid,String.valueOf(30))){
                Thread.sleep(50);
            }
            return true;
        } catch (Exception e) {
            return  false;
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException{
        return true;
    }

    @Override
    public void unlock() {
        String uuid=IdUtil.simpleUUID()+":"+Thread.currentThread().getId();
        String script =
                "if redis.call('HEXISTS',KEYS[1],ARGV[1]) == 0 then " +
                        "   return nil " +
                        "elseif redis.call('HINCRBY',KEYS[1],ARGV[1],-1) == 0 then " +
                        "   return redis.call('del',KEYS[1]) " +
                        "else " +
                        "   return 0 " +
                        "end";
        redisTemplate.execute(new DefaultRedisScript(script,Boolean.class),Arrays.asList(lockName),uuid);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public String getType() {
        return "redis";
    }
    private void renewExpire(){
        String script =
                "if redis.call('HEXISTS',KEYS[1],ARGV[1]) == 1 then " +
                        "return redis.call('expire',KEYS[1],ARGV[2]) " +
                        "else " +
                        "return 0 " +
                        "end";
        String uuidValue=IdUtil.simpleUUID()+":"+Thread.currentThread().getId();

        new Timer().schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                if ((Boolean)redisTemplate.execute(new DefaultRedisScript<>(script, Boolean.class), Arrays.asList(lockName),uuidValue,String.valueOf(expireTime))) {
                    renewExpire();
                }
            }
        },(this.expireTime * 1000)/3);
    }
}
