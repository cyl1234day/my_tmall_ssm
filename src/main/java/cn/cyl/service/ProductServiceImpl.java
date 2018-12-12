package cn.cyl.service;

import cn.cyl.entity.Category;
import cn.cyl.entity.Product;
import cn.cyl.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengyl
 * @create 2018-09-15-14:59
 */
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductMapper mapper;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ReviewService reviewService;

    @Override
    public List<Product> listByCategory(Category c) {
        return mapper.listByCategory(c);
    }

    @Override
    public List<Product> listByFuzzy(String keyword) {
        return mapper.listByFuzzy(keyword);
    }

    @Override
    public void addProduct(Product product) {
        mapper.addProduct(product);
    }

    @Override
    public void deleteProduct(Product product) {
        mapper.deleteProduct(product);
    }

    @Override
    public Product findOneById(int id) {
        return mapper.findOneById(id);
    }

    @Override
    public void updateProduct(Product product) {
        mapper.updateProduct(product);
    }

    @Override
    public void fill(List<Category> categoryList) {
        for(Category c : categoryList){
            List<Product> productList =  this.listByCategory(c);

            if(productList != null && productList.size() != 0){
                for(Product p : productList){
                    productImageService.setShowImage(p);
                    orderItemService.setSaleCount(p);
                    reviewService.setReviewCount(p);
                }
                c.setProducts(productList);
            }
        }
    }

    @Override
    public void fillByRow(List<Category> categoryList) {
        int countPerRow = 8;
        for (Category c : categoryList) {
            List<List<Product>> outerList = new ArrayList<>();
            List<Product> innerList = new ArrayList<>();
            List<Product> productList =  this.listByCategory(c);
            if(productList != null && productList.size() != 0){
                if(productList.size() <= countPerRow){
                    innerList = productList;
                    outerList.add(innerList);
                } else {
                    int row = (productList.size() / (countPerRow + 1)) + 1 ;
                    int i = 0;
                    for(i = 0; i < row; i++){
                        innerList = productList.subList(i*countPerRow, (i+1)*countPerRow);
                        outerList.add(innerList);
                    }
                    innerList = productList.subList( i*countPerRow, productList.size() );
                    outerList.add(innerList);
                }

            }
            c.setProductsByRow(outerList);
        }
    }
}
