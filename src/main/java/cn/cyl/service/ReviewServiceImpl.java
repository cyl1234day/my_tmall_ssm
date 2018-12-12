package cn.cyl.service;

import cn.cyl.entity.Product;
import cn.cyl.entity.Review;
import cn.cyl.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chengyl
 * @create 2018-09-16-23:42
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewMapper mapper;

    @Override
    public void addReview(Review review) {
        mapper.addReview(review);
    }

    @Override
    public List<Review> listByProduct(Product p) {
        return mapper.listByProduct(p);
    }

    @Override
    public int reviewCountByProduct(Product p) {
        return (int) mapper.reviewCountByProduct(p);
    }

    @Override
    public void setReviewCount(Product product) {
        int count = (int) this.reviewCountByProduct(product);
        product.setReviewCount(count);
    }
}
