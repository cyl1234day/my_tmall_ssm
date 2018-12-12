package cn.cyl.mapper;

import cn.cyl.entity.ProductImage;

import java.util.List;
import java.util.Map;

/**
 * @author chengyl
 * @create 2018-09-15-16:28
 */
public interface ProductImageMapper {

    //插入记录
    public void addProductImage(ProductImage productImage);

    //根据 产品Product和图片类型type 返回对象集合
    //因为Mybatis只能传入一个参数，所有有多个查询条件的时候封装成一个map
    public List<ProductImage> list(Map<String, Object> map);

    //删除记录
    public void deleteProductImage(ProductImage productImage);

    //根据id查找记录
    public ProductImage findOneById(int id);
}
