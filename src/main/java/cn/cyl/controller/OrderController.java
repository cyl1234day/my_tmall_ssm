package cn.cyl.controller;

import cn.cyl.entity.Order;
import cn.cyl.entity.OrderItem;
import cn.cyl.service.OrderItemService;
import cn.cyl.service.OrderService;
import cn.cyl.service.ProductImageService;
import cn.cyl.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author chengyl
 * @create 2018-09-16-23:27
 */
@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ProductImageService productImageService;

    @RequestMapping("admin_order_list")
    public String listOrder(Page page, Model model) {
        if(page == null) {
            page = new Page(0,5);
        }

        PageHelper.offsetPage(page.getStart(), page.getCountPerPage());
        List<Order> list = orderService.listAll();
        long total = new PageInfo<>(list).getTotal();
        page.setTotal(total);

        if(list != null) {
            for(Order o : list) {
                //给order设置它所拥有的订单项
                orderItemService.fillOrder(o);
                //设置商品总数和总金额，两者的顺序不能换，否则会出现空指针异常
                orderService.fillOrder(o);
                //给order中的产品设置展示图片
                for(OrderItem oi : o.getOrderItems()) {
                    productImageService.setShowImage(oi.getProduct());
                }
                //设置order的中文状态
                orderService.translateOrderStatus(o);
            }

        }
        model.addAttribute("page", page);
        model.addAttribute("orders", list);
        return "admin/listOrder";
    }

}
