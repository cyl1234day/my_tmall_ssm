package cn.cyl.service;

import cn.cyl.entity.Order;
import cn.cyl.entity.OrderItem;
import cn.cyl.entity.Product;
import cn.cyl.entity.User;
import cn.cyl.mapper.OrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chengyl
 * @create 2018-09-16-15:45
 */
@Service
public class OrderItemServiceImpl implements OrderItemService{

    @Autowired
    OrderItemMapper mapper;

    @Override
    public void addOrderItem(OrderItem orderItem) {
        mapper.addOrderItem(orderItem);
    }

    @Override
    public OrderItem findOneById(int id) {
        return mapper.findOneById(id);
    }

    @Override
    public OrderItem findOrderItemBeforeGenOrder(Product product, User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("product", product);
        map.put("user", user);
        return mapper.findOrderItemBeforeGenOrder(map);
    }

    @Override
    public int findSellCountByProduct(Product product) {
        //使用sum()聚合函数时，如果最后没有结果返回NULL而不是0，所以要使用包装类来接收结果
        Long count = mapper.findSellCountByProduct(product);
        if(count == null) {
            //如果为null，返回0
            count = 0L;
        }
        //转换成int
        return count.intValue();
    }

    @Override
    public List<OrderItem> findOrderItemsInCart(User user) {
        return mapper.findOrderItemsInCart(user);
    }

    @Override
    public List<OrderItem> findOrderItemsByOrder(Order order) {
        return mapper.findOrderItemsByOrder(order);
    }

    @Override
    public void updateOrderItem(OrderItem orderItem) {
        mapper.updateOrderItem(orderItem);
    }

    @Override
    public void deleteOrderItem(int id) {
        mapper.deleteOrderItem(id);
    }

    @Override
    public void fillOrder(Order o) {
        List<OrderItem> list = this.findOrderItemsByOrder(o);
        if(list != null){
            o.setOrderItems(list);
        }
    }

    @Override
    public void setSaleCount(Product p) {
        p.setSaleCount(this.findSellCountByProduct(p));
    }
}
