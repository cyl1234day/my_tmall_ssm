package cn.cyl.service;

import cn.cyl.entity.Product;
import cn.cyl.entity.Review;

import java.util.List;

/**
 * @author chengyl
 * @create 2018-09-16-23:42
 */
public interface ReviewService {

    //添加评价
    public void addReview(Review review);

    //根据产品返回所有评价
    public List<Review> listByProduct(Product p);

    //根据产品名称返回总的评价数量
    public int reviewCountByProduct(Product p);

    //给商品设置review总数
    void setReviewCount(Product product);
}
