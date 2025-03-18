package spring.service.impl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import spring.dao.OrderDao;
import spring.entity.Order;
import spring.service.OrderService;

import javax.annotation.Resource;

/**
 * (Order)表服务实现类
 *
 * @author makejava
 * @since 2024-07-05 18:03:53
 */
@Service
public class OrderServiceImpl implements OrderService, CommandLineRunner {
    @Resource
    private OrderDao orderDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Order queryById(Integer id) {
        return this.orderDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param order 实例对象
     * @return 实例对象
     */
    @Override
    public Order insert(Order order) {
        this.orderDao.insert(order);
        return order;
    }

    /**
     * 修改数据
     *
     * @param order 实例对象
     * @return 实例对象
     */
    @Override
    public Order update(Order order) {
        this.orderDao.update(order);
        return this.queryById(order.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.orderDao.deleteById(id) > 0;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("测试CommandLineRunner接口");
    }
}
