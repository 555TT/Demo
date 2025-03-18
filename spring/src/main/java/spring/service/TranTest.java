package spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.dao.OrderDao;
import spring.entity.Order;

import javax.annotation.Resource;

/**
 * @author: wanghaoran1
 * @create: 2025-03-17
 */
@Service
public class TranTest {


    @Resource
    private OrderDao orderDao;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void fun1() {
        orderDao.insert(new Order(3, "apple", 1));
        fun2();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void fun2() {
        orderDao.insert(new Order(2, "banana", 2));
        int i = 1 / 0;
    }
}
