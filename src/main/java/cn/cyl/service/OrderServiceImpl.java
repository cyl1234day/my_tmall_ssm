package cn.cyl.service;

import cn.cyl.entity.Order;
import cn.cyl.entity.OrderItem;
import cn.cyl.entity.Status;
import cn.cyl.entity.User;
import cn.cyl.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chengyl
 * @create 2018-09-16-17:04
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper mapper;

    @Autowired
    OrderItemService orderItemService;

    @Override
    public List<Order> listAll() {
        return mapper.listAll();
    }

    @Override
    public void addOrder(Order order) {
        mapper.addOrder(order);
    }

    @Override
    public Order findOneById(int id) {
        return mapper.findOneById(id);
    }

    @Override
    public List<Order> findOrderByUser(User user) {
        return mapper.findOrderByUser(user);
    }

    @Override
    public void updateOrder(Order order) {
        mapper.updateOrder(order);
    }

    @Override
    public void deleteOrder(OrderItem order) {
        mapper.deleteOrder(order);
    }

    @Override
    @Transactional
    public void createOrder(Order order, List<OrderItem> orderItems) {
        for(OrderItem oi : orderItems) {
			oi.setOrder(order);
			orderItemService.updateOrderItem(oi);
		}
    }

    @Override
    public void fillOrder(Order o) {
        int totalNum = 0;
        double total = 0;
        List<OrderItem> list = o.getOrderItems();
        for(OrderItem oi : list){
            totalNum += oi.getNumber();
            total += (oi.getProduct().getPromotePrice() * oi.getNumber());
        }
        o.setTotal(total);
        o.setTotalNumber(totalNum);
    }

    @Override
    public void translateOrderStatus(Order o) {
        String str = Status.valueOf(o.getStatus()).getName();
        o.setStatusCN(str);
    }


}
