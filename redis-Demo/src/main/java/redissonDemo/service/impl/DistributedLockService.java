package redissonDemo.service.impl;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import redissonDemo.util.lock.DistributedLockFactory;
import redissonDemo.util.lock.MyLock;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author: 小手WA凉
 * @create: 2024-07-10
 */
@Service
@Slf4j
public class DistributedLockService {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private DistributedLockFactory lockFactory;

    /**
     * 使用redis的hash结构实现锁的可重入性，使用简单工厂+策略模式实现锁的替换
     * k                 k                v
     * lockName      uuid:ThreadId        重入次数
     */
    public void sale(){
        MyLock redisLock = lockFactory.getLock("redis");
        redisLock.tryLock();
        try {
            String result = (String)redisTemplate.opsForValue().get("inventory001");
            int inventorNum =result==null?0:Integer.parseInt(result);
            if(inventorNum>0){
                redisTemplate.opsForValue().set("inventory001",String.valueOf(--inventorNum));
                log.info("卖出一个商品，剩余库存：{}",inventorNum);
            }else {
                log.info("商品买完了");
            }
        } finally {
           redisLock.unlock();
        }
    }




    /**
     *功能：判断是自己加的锁后再删除，判断删除操作用lua脚本实现原子性
     */
    public void sale2(){
        String key="redisLock";
        String uuidValue= IdUtil.simpleUUID()+":"+Thread.currentThread().getId();
        while(!redisTemplate.opsForValue().setIfAbsent(key,uuidValue,30L, TimeUnit.SECONDS)){
            try {
                Thread.sleep(20L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            String result = (String)redisTemplate.opsForValue().get("inventory001");
            int inventorNum =result==null?0:Integer.parseInt(result);
            if(inventorNum>0){
                redisTemplate.opsForValue().set("inventory001",String.valueOf(--inventorNum));
                log.info("卖出一个商品，剩余库存：{}",inventorNum);
            }else {
                log.info("商品买完了");
            }
        } finally {
            String luaScript="if(redis.call('get',KEYS[1])==ARGV[1]) then return redis.call('del',KEYS[1]) else return 0 end";
            redisTemplate.execute(new DefaultRedisScript(luaScript, Boolean.class), Arrays.asList(key),uuidValue);
        }
    }




    /**
     * 功能：带过期时间的setnx加锁，finally再次手动解锁
     * 问题：可能会删除别人的锁
     * 当业务执行时间较长，业务还没执行完，锁过期了，另一个线程拿到锁进来了，第一个线程执行finally把另一个线程加的锁删除了
     */
    public void sale1(){
        String key="redisLock";
        String uuidValue= IdUtil.simpleUUID()+":"+Thread.currentThread().getId();
        while(!redisTemplate.opsForValue().setIfAbsent(key,uuidValue,30L, TimeUnit.SECONDS)){
            try {
                Thread.sleep(20L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            String result = (String)redisTemplate.opsForValue().get("inventory001");
            int inventorNum =result==null?0:Integer.parseInt(result);
            if(inventorNum>0){
                redisTemplate.opsForValue().set("inventory001",String.valueOf(--inventorNum));
                log.info("卖出一个商品，剩余库存：{}",inventorNum);
            }else {
                log.info("商品买完了");
            }
        } finally {
            redisTemplate.delete(key);
        }
    }
}
