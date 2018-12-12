package cn.cyl.fore.controller;

import cn.cyl.entity.OrderItem;
import cn.cyl.entity.User;
import cn.cyl.service.CategoryService;
import cn.cyl.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author chengyl
 * @create 2018-09-17-12:52
 */
public class OtherInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    CategoryService categoryService;

    @Autowired
    OrderItemService orderItemService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        HttpSession session = request.getSession();

        //获取购物车中的商品数量
        User user = (User) session.getAttribute("user");
        int  cartTotalItemNumber = 0;
        if(null != user) {
            List<OrderItem> ois = orderItemService.findOrderItemsInCart(user);
            for (OrderItem oi : ois) {
                cartTotalItemNumber += oi.getNumber();
            }

        }
        session.setAttribute("cartTotalItemNumber", cartTotalItemNumber);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
