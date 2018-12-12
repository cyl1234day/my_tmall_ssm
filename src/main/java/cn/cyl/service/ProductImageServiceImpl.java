package cn.cyl.service;

import cn.cyl.entity.Product;
import cn.cyl.entity.ProductImage;
import cn.cyl.mapper.ProductImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chengyl
 * @create 2018-09-15-19:19
 */
@Service
public class ProductImageServiceImpl implements ProductImageService{

    @Autowired
    ProductImageMapper mapper;

    @Override
    public void addProductImage(ProductImage productImage) {
        mapper.addProductImage(productImage);
    }

    @Override
    public List<ProductImage> list(Product p, String type) {
        Map<String, Object> map = new HashMap<>();
        map.put("product", p);
        map.put("type", type);
        return mapper.list(map);
    }

    @Override
    public void deleteProductImage(ProductImage productImage) {
        mapper.deleteProductImage(productImage);
    }

    @Override
    public ProductImage findOneById(int id) {
        return mapper.findOneById(id);
    }

    //设置第一张展示图片
    public void setShowImage(Product p) {
        List<ProductImage> list = this.list(p, "type_show");
        if(list != null && list.size() != 0){
            p.setShowImage(list.get(0));
        }
    }

    //设置展示图片
    @Override
    public void setShowImages(Product product) {
        List<ProductImage> list = this.list(product, "type_show");
        product.setProductShowImages(list);
    }

    //设置详情图片
    @Override
    public void setDetailImages(Product product) {
        List<ProductImage> list = this.list(product, "type_detail");
        product.setProductDetailImages(list);
    }
}
