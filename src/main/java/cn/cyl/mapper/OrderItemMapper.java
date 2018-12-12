package cn.cyl.mapper;

import cn.cyl.entity.Order;
import cn.cyl.entity.OrderItem;
import cn.cyl.entity.Product;
import cn.cyl.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author chengyl
 * @create 2018-09-16-15:41
 */
public interface OrderItemMapper {

    //新增订单项
    public void addOrderItem(OrderItem orderItem);

    //根据id查找
    public OrderItem findOneById(int id);

    //根据商品和用户查找还未生成订单(已加入到购物车中)的订单项
    public OrderItem findOrderItemBeforeGenOrder(Map<String, Object> map);

    //根据商品查找总销量
    public Long findSellCountByProduct(Product product);

    //根据用户名查找加入到购物车中的商品
    public List<OrderItem> findOrderItemsInCart(User user);

    //根据订单获取该订单的所有订单项
    public List<OrderItem> findOrderItemsByOrder(Order order);

    //更新订单项
    public void updateOrderItem(OrderItem orderItem);

    //删除订单项
    public void deleteOrderItem(int id);
}
