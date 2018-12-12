package cn.cyl.mapper;

import cn.cyl.entity.Order;
import cn.cyl.entity.OrderItem;
import cn.cyl.entity.User;

import java.util.List;

/**
 * @author chengyl
 * @create 2018-09-16-17:00
 */
public interface OrderMapper {

    //查询所有订单
    public List<Order> listAll();

    //新增订单项
    public void addOrder(Order order);

    //根据id查找
    public Order findOneById(int id);

    //根据用户名查找订单，但是不要查找状态位  删除和已完成  的订单
    public List<Order> findOrderByUser(User user);

    //更新订单
    public void updateOrder(Order order);

    //删除订单
    public void deleteOrder(OrderItem order);
}
