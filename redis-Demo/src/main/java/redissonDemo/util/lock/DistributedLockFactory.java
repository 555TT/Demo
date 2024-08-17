package redissonDemo.util.lock;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author: 小手WA凉
 * @create: 2024-07-10
 */
@Component
public class DistributedLockFactory implements InitializingBean {
    Map<String,MyLock> lockFactory=new HashMap<>();
    @Resource
    private List<MyLock> locks=new LinkedList<>();
    public  MyLock getLock(String type){
        return lockFactory.get(type);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for(MyLock lock:locks){
            lockFactory.put(lock.getType(),lock);
        }
    }
}
