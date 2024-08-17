package spring.test;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.entity.Order;
import spring.service.OrderService;
import javax.annotation.Resource;

/**
 * @author: 小手WA凉
 * @create: 2024-07-05
 */
@Service
public class Test {
    @Resource
    private OrderService orderService;

    public  void update(){
        Order order = new Order();
        order.setProductName("book");
        order.setId(1);
        orderService.update(order);
        update2();
        int i=1/0;
    }
    @Transactional()
    public void update2(){
        Order order = new Order();
        order.setNum(2);
        order.setId(1);
        orderService.update(order);
    }
}
