package cn.cyl.service;

import cn.cyl.entity.Product;
import cn.cyl.entity.Property;
import cn.cyl.entity.PropertyValue;

import java.util.List;

/**
 * @author chengyl
 * @create 2018-09-16-0:01
 */
public interface PropertyValueService {

    //根据产品 查询 其所有的属性值
    public List<PropertyValue> listByProduct(Product product);

    //根据id返回记录
    public PropertyValue findOneById(int id);

    //根据产品和属性名 查询 唯一的一条记录
    public PropertyValue findOneByPropertyAndProduct(Property property, Product product);

    //插入记录，一般用来初始化
    public void addPropertyValue(PropertyValue pv);

    //更新记录
    public void updatePropertyValue(PropertyValue pv);

    //初始化属性值
    void initValue(Property p, Product product);
}
