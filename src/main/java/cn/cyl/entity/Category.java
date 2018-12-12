package cn.cyl.entity;

import java.util.List;

/**
 * @author chengyl
 * @create 2018-09-14-16:07
 */
public class Category {

    private Integer id;
    private String name;

//=============前端展示所需要的属性====================================

    //每个分类下的所有product
    List<Product> products;

    //每个8个产品分为一行
    List<List<Product>> productsByRow;

//=================getter and setter======================================

    public Integer getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<List<Product>> getProductsByRow() {
        return productsByRow;
    }

    public void setProductsByRow(List<List<Product>> productsByRow) {
        this.productsByRow = productsByRow;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + "]";
    }

}
