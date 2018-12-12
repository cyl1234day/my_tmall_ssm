package cn.cyl.mapper;

import cn.cyl.entity.Category;
import cn.cyl.entity.Product;

import java.util.List;

/**
 * @author chengyl
 * @create 2018-09-15-14:54
 */
public interface ProductMapper {

    //根据分类来查询下面的所有产品
    public List<Product> listByCategory(Category c);

    //根据关键词模糊查询
    public List<Product> listByFuzzy(String keyword);

    //插入记录
    public void addProduct(Product product);

    //删除记录
    public void deleteProduct(Product product);

    //根据id查找记录
    public Product findOneById(int id);

    //修改记录
    public void updateProduct(Product product);
}
