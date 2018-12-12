package cn.cyl.fore.controller;

import cn.cyl.entity.*;
import cn.cyl.service.*;
import cn.cyl.util.GenOrderCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author chengyl
 * @create 2018-09-16-9:31
 */
@Controller
public class forecontroller {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    PropertyValueService propertyValueService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderService orderService;

    private boolean flagToSort = false;

    private boolean firstToReview = true;


    //页面显示数据
    @RequestMapping("forehome")
    public String home(HttpSession session) {

        List<Category> categoryList = categoryService.listAll();
        //给分类填充数据
        productService.fill(categoryList);
        productService.fillByRow(categoryList);
        session.setAttribute("categorys", categoryList);
        session.removeAttribute("keyword");
        return "redirect:/home.jsp";
    }

    //注册用户
    @RequestMapping("foreregister")
    public String register(User user, HttpSession session) {
        if(userService.isExist(user)) {
            String msg = "用户名已存在";
            session.setAttribute("msg", msg);
            return "redirect:/register.jsp";
        } else {
            userService.addUser(user);
            return "redirect:/registerSuccess.jsp";
        }

    }


    //登录用户
    @RequestMapping("forelogin")
    public String login(User user, HttpSession session) {
        String msg = null;
        User db_user = userService.findOneByName(user.getName());
        if(db_user == null) {
            msg = "用户不存在！";
            session.setAttribute("msg", msg);
            return "redirect:/login.jsp";
        }
        if(!db_user.getPassword().equals(user.getPassword())) {
            msg = "密码错误！";
            session.setAttribute("msg", msg);
            return "redirect:/login.jsp";
        }
        session.setAttribute("user", db_user);
        return "redirect:/forehome";
    }


    //用户退出
    @RequestMapping("forelogout")
    public String logout(HttpSession session) {
        //session.setAttribute("user", null);

        //两者效果类似
        session.removeAttribute("user");
        return "redirect:/home.jsp";
    }


    //产品详情页
    @RequestMapping("foreproduct")
    public String detailProduct(int pid, HttpSession session) {
        //根据id查找商品
        Product product = productService.findOneById(pid);
        //给商品设置展示的第一张图片
        productImageService.setShowImage(product);
        //给商品设置展示图片
        productImageService.setShowImages(product);
        //给商品设置详细图片
        productImageService.setDetailImages(product);
        //设置总评价数
        reviewService.setReviewCount(product);
        //设置总销量
        orderItemService.setSaleCount(product);
        //获取该商品的所有评价
        List<Review> reviewList = reviewService.listByProduct(product);
        //获取该商品的所有属性和属性值
        List<PropertyValue> pvList = propertyValueService.listByProduct(product);

        session.setAttribute("product", product);
        session.setAttribute("reviews", reviewList);
        session.setAttribute("propertyValues", pvList);
        return "redirect:/product.jsp";
    }



    //判断是否已经登录
    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkIfLogin(HttpSession session) {
        Object o = session.getAttribute("user");
        if(o != null) {
            return "success";
        } else {
            return "failed";
        }
    }



    //使用模态窗进行登录
    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String LoginByModal(User user, HttpSession session) {
        User db_user = userService.findOneByName(user.getName());
        if(db_user == null) {
            return "failed";
        }
        if(!db_user.getPassword().equals(user.getPassword())) {
            return "failed";
        }
        session.setAttribute("user", db_user);
        return "success";
    }


    /**
     * 前端的路径要写成    forecategory?cid=${category.id}&sort=all  才能访问到后端的Controller
     * 如果只写成  ?cid=${category.id}&sort=all  会跳转不过来
     */
    //产品分类页显示并排序
    @RequestMapping("forecategory")
    public String showProduct(int cid, String sort, HttpSession session) {

        Category category = categoryService.findOneById(cid);
        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        productService.fill(categoryList);

        if(sort != null) {

            switch(sort) {
                case "all" :
                    Collections.sort(category.getProducts(), new Comparator<Product>(){
                        @Override
                        public int compare(Product p1, Product p2) {
                            return (p2.getReviewCount()*p2.getSaleCount()) - (p1.getReviewCount()*p1.getSaleCount());
                        }
                    });
                    flagToSort = false;
                    break;
                case "review" :
                    Collections.sort(category.getProducts(), new Comparator<Product>(){
                        @Override
                        public int compare(Product p1, Product p2) {
                            return p2.getReviewCount() - p1.getReviewCount();
                        }
                    });
                    flagToSort = false;
                    break;
                case "date" :
                    Collections.sort(category.getProducts(), new Comparator<Product>(){
                        @Override
                        public int compare(Product p1, Product p2) {
                            return p1.getCreateDate().compareTo(p2.getCreateDate());
                        }
                    });
                    flagToSort = false;
                    break;
                case "saleCount" :
                    Collections.sort(category.getProducts(), new Comparator<Product>(){
                        @Override
                        public int compare(Product p1, Product p2) {
                            return p2.getSaleCount() - p1.getSaleCount();
                        }
                    });
                    flagToSort = false;
                    break;
                case "price" :
                    Collections.sort(category.getProducts(), new Comparator<Product>(){
                        @Override
                        public int compare(Product p1, Product p2) {
                            return (int) (p2.getPromotePrice() - p1.getPromotePrice());
                        }
                    });
                    if(!flagToSort) {
                        flagToSort = true;
                    } else {
                        flagToSort = false;
                        Collections.reverse(category.getProducts());
                    }
                    break;
            }
        }

        session.setAttribute("category", category);
        return "redirect:/category.jsp";
    }


    //根据关键字进行搜索
    @RequestMapping("foresearch")
    public String search(String keyword, HttpSession session) {
        List<Product> list = productService.listByFuzzy(keyword);
        //设置展示图片和销量
        if(list != null) {
            for(Product p : list) {
                productImageService.setShowImage(p);
                reviewService.setReviewCount(p);
                //设置销量
                orderItemService.setSaleCount(p);
            }
        }
        //把关键字传回去
        session.setAttribute("keyword", keyword);
        session.setAttribute("products", list);
        return "redirect:/searchResult.jsp";
    }


    /*
     * forebuyone?pid=${product.id}&num={num}
     */
    //立即购买功能(只有一件商品，直接生成订单)
    @RequestMapping("forebuyone")
    public String buyone(int pid, int num, HttpSession session) {
        //根据id查询商品
        Product product = productService.findOneById(pid);
        //获取当前登录的用户
        User user = (User) session.getAttribute("user");

        OrderItem orderitem = new OrderItem();
        orderitem.setProduct(product);
        orderitem.setNumber(num);
        orderitem.setUser(user);

        List<OrderItem> list = new LinkedList<>();
        productImageService.setShowImage(orderitem.getProduct());
        //必须要装到List中，前端的forEach才能遍历
        list.add(orderitem);
        double totalMoney = orderitem.getProduct().getPromotePrice()*orderitem.getNumber();
        //表示立即购买的订单项
        session.setAttribute("buyNowItem", orderitem);
        //和购物车公用的页面，要封装成list才能遍历
        session.setAttribute("orderItems", list);
        //付款总额，后期可以加上运费
        session.setAttribute("total", totalMoney);
        //防止进入购物车结算后中途退出，session域中仍存在buyByCart而造成干扰
        session.removeAttribute("buyByCart");
        return "redirect:/buy.jsp";
    }


    //加入购物车操作，不进行页面跳转，ajax内部数据传递
    @RequestMapping("foreaddCart")
    @ResponseBody
    public String addCart(int pid, int num, HttpSession session) {
        //根据id查询商品
        Product product = productService.findOneById(pid);
        //获取当前登录的用户
        User user = (User) session.getAttribute("user");
        //查找是否已经有加入购物车但还未生成订单的项
        OrderItem db_orderItem = orderItemService.findOrderItemBeforeGenOrder(product, user);
        //如果有，则增加数量
        if(db_orderItem != null) {
            int newNum = db_orderItem.getNumber() + num;
            db_orderItem.setNumber(newNum);
            orderItemService.updateOrderItem(db_orderItem);
        } else {
            //如果没有，则新增一条记录
            OrderItem orderitem = new OrderItem();
            orderitem.setProduct(product);
            orderitem.setNumber(num);
            orderitem.setUser(user);
            orderItemService.addOrderItem(orderitem);
        }
        return "success";
    }



    //查看购物车界面
    @RequestMapping("forecart")
    public String checkCart(HttpSession session) {
        //根据当前登录用户，查找所有未生成订单的项
        User user = (User) session.getAttribute("user");
        if(user == null) {
            return "redirect:/login.jsp";
        }
        List<OrderItem> list = orderItemService.findOrderItemsInCart(user);
        if(list != null) {
            for(OrderItem oi : list) {
                //设置展示图片
                productImageService.setShowImage(oi.getProduct());
            }
        }
        session.setAttribute("orderItems", list);
        return "redirect:/cart.jsp";
    }


    //删除订单项
    @RequestMapping("foredeleteOrderItem")
    @ResponseBody
    public String deleteOrderItem(int oiid) {
        orderItemService.deleteOrderItem(oiid);
        return "success";
    }


    //更新订单项，注意传回的参数是商品id，而不是订单项id
    @RequestMapping("forechangeOrderItem")
    @ResponseBody
    public String updateOrderItem(int pid, int num, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Product product = productService.findOneById(pid);
        OrderItem oi = orderItemService.findOrderItemBeforeGenOrder(product, user);
        oi.setNumber(num);
        orderItemService.updateOrderItem(oi);
        return "success";
    }


    //点击‘结算’按钮后，进入填写用户信息的页面
    @RequestMapping("forebuy")
    public String CartForBuy(int[] oiids, HttpSession session) {
        double totalMoney = 0;
        List<OrderItem> list = new LinkedList<>();
        for(int oiid : oiids) {
            OrderItem orderItem = orderItemService.findOneById(oiid);
            productImageService.setShowImage(orderItem.getProduct());
            totalMoney += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
            list.add(orderItem);
        }
        session.setAttribute("orderItems", list);
        session.setAttribute("total", totalMoney);
        //在结算页面用于区分是立即购买还是加入购物车
        session.setAttribute("buyByCart", list);
        //防止进入立即购买的结算页面后中途退出，session域中仍存在buyByCart而造成干扰
        session.removeAttribute("buyNowItem");
        return "redirect:/buy.jsp";
    }


    //提交订单
    @RequestMapping("forecreateOrder")
    public String createOrder(Order order, HttpSession session) {
        User user = (User) session.getAttribute("user");

        List<OrderItem> orderItems = (List<OrderItem>) session.getAttribute("buyByCart");
        if(orderItems == null) {
            //说明是点击 立即购买 进来的
            OrderItem orderItem = (OrderItem) session.getAttribute("buyNowItem");
            orderItemService.addOrderItem(orderItem);
            orderItems = new ArrayList<>();
            orderItems.add(orderItem);

        } else {
            //说明是点击   购物车 结算  进来的
        }

        //生成订单
        order.setOrderCode(GenOrderCode.genOrderCode());
        order.setCreateDate(new Date());
        order.setStatus(Status.WAITPAY.toString());
        order.setUser(user);

//**********************************************

		//先存入数据库，这样才会有id
		orderService.addOrder(order);

//		for(OrderItem oi : orderItems) {
//			oi.setOrder(order);
//			orderItemService.updateOrderItem(oi);
//		}

//**********************************************
//中间这段代码很重要，要么全部成功，要么全部失败，不允许中间出现异常，所有放在一个事务方法中。

        orderService.createOrder(order, orderItems);
        session.setAttribute("order", order);
        //移除Session属性
        session.removeAttribute("buyNowItem");
        session.removeAttribute("buyByCart");

        return "redirect:/alipay.jsp";
    }



    //用于从  我的订单    进来的付款页面
    /*
     * 一定要加个total参数而不能从session域中读取的原因是可能下单后不是立即付款，
     * 而是下次登录时通过我的订单来付款，此时session域中没有该属性，所以要自己带上这个参数
     */
    @RequestMapping("forealipay")
    public String toPayPage(int oid, double total, HttpSession session) {
        Order order = orderService.findOneById(oid);
        session.setAttribute("order", order);
        session.setAttribute("total", total);
        return "redirect:/alipay.jsp";
    }


    //点击  确认付款  按钮后
    @RequestMapping("forepayed")
    public String pay(int oid, HttpSession session) {
        //根据订单编号查询订单
        Order order = orderService.findOneById(oid);
        //设置付款日期
        order.setPayDate(new Date());
        //更改订单状态为待发货
        order.setStatus(Status.WAITDELIVERY.toString());
        orderService.updateOrder(order);
        session.setAttribute("order", order);
        return "redirect:/payed.jsp";
    }


    //我的订单
    @RequestMapping("foreorder")
    public String checkMyOrder(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null) {
            return "redirect:/login.jsp";
        }
        //不包括已删除和已完成的订单
        List<Order> orders = orderService.findOrderByUser(user);
        if(orders != null) {
            for(Order o : orders) {
                o.setUser(user);
                //给order设置它所拥有的订单项
                orderItemService.fillOrder(o);
                //设置商品总数和总金额，两者的顺序不能换，否则会出现空指针异常
                orderService.fillOrder(o);
                //给order中的产品设置展示图片
                for(OrderItem oi : o.getOrderItems()) {
                    productImageService.setShowImage(oi.getProduct());
                }
            }
        }
        session.setAttribute("orders", orders);
        return "redirect:/myOrder.jsp";
    }



    //删除订单(删除订单不是真正从数据库中把记录删掉，而是把状态位置为delete)
    @RequestMapping("foredeleteOrder")
    @ResponseBody
    public String deleteOrder(int oid) {
        Order order = orderService.findOneById(oid);
        order.setStatus(Status.DELETE.toString());
        orderService.updateOrder(order);
        return "success";
    }


    //发货(后台的功能),更新订单状态，然后重新请求 admin_order_list
    @RequestMapping("admin_order_delivery")
    public String deliveryOrder(int oid) {
        Order order = orderService.findOneById(oid);
        order.setStatus(Status.WAITCONFIRM.toString());
        order.setDeliveryDate(new Date());
        orderService.updateOrder(order);
        return "redirect:/admin_order_list";
    }


    //确认收货的页面跳转
    @RequestMapping("foreconfirmPay")
    public String confirmOrder(int oid, HttpSession session) {
        Order order = orderService.findOneById(oid);
        //给order设置它所拥有的订单项
        orderItemService.fillOrder(order);
        //设置商品总数和总金额，两者的顺序不能换，否则会出现空指针异常
        orderService.fillOrder(order);
        //给order中的产品设置展示图片
        for(OrderItem oi : order.getOrderItems()) {
            productImageService.setShowImage(oi.getProduct());
        }
        session.setAttribute("order", order);
        return "redirect:/confirmPay.jsp";
    }


    //真正的确认收货
    @RequestMapping("foreorderConfirmed")
    public String realConfirmOrder(int oid) {
        Order order = orderService.findOneById(oid);
        //设置确认收货时间
        order.setConfirmDate(new Date());
        //更新订单状态
        order.setStatus(Status.WAITREVIEW.toString());
        orderService.updateOrder(order);
        return "redirect:/orderConfirmed.jsp";
    }



    //跳转到评价商品页面(目前只实现了评价订单中第一个商品的功能)
    @RequestMapping("forereview")
    public String toReviewOrder(int oid, HttpSession session) {
        if(session.getAttribute("showonly") == null) {
            firstToReview = true;
        }
        if(firstToReview) {
            session.removeAttribute("showonly");
            //
        } else {
            firstToReview = true;
        }

        Order order = orderService.findOneById(oid);
        orderItemService.fillOrder(order);
        //获取订单中的第一个商品
        Product product = order.getOrderItems().get(0).getProduct();
        //给商品设置展示图片
        productImageService.setShowImage(product);
        //给商品设置review总数
        reviewService.setReviewCount(product);
        //给商品设置sale总数
        orderItemService.setSaleCount(product);
        //获取此商品的所有评价
        List<Review> reviews = reviewService.listByProduct(product);

        session.setAttribute("product", product);
        session.setAttribute("reviews", reviews);
        session.setAttribute("order", order);
        return "redirect:/review.jsp";
    }


    //真正的评价商品
    @RequestMapping("foredoreview")
    public String reviewOrder(String content, int pid, int oid, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Order order = orderService.findOneById(oid);
        Product product = productService.findOneById(pid);

        //新建一个评价
        Review review = new Review();
        review.setContent(content);
        review.setCreateDate(new Date());
        review.setProduct(product);
        review.setUser(user);
        reviewService.addReview(review);

        //更新订单状态
        order.setStatus(Status.FINISH.toString());
        orderService.updateOrder(order);

        session.setAttribute("showonly", true);
        firstToReview = false;

        return "redirect:/forereview?oid="+oid;
    }

}
