package cn.cyl.service;

import cn.cyl.entity.Product;
import cn.cyl.entity.ProductImage;

import java.util.List;

/**
 * @author chengyl
 * @create 2018-09-15-19:18
 */
public interface ProductImageService {

    //插入记录
    public void addProductImage(ProductImage productImage);

    //根据 产品Product和图片类型type 返回对象集合
    public List<ProductImage> list(Product p, String type);

    //删除记录
    public void deleteProductImage(ProductImage productImage);

    //根据id查找记录
    public ProductImage findOneById(int id);

    //设置第一张展示图片
    public void setShowImage(Product p);

    //给商品设置展示图片
    void setShowImages(Product product);

    //给商品设置详情图片
    void setDetailImages(Product product);
}
