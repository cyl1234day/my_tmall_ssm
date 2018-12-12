package cn.cyl.mapper;

import cn.cyl.entity.Product;
import cn.cyl.entity.PropertyValue;

import java.util.List;
import java.util.Map;

/**
 * @author chengyl
 * @create 2018-09-15-23:58
 */
public interface PropertyValueMapper {

    //根据产品 查询 其所有的属性值
    public List<PropertyValue> listByProduct(Product product);

    //根据id返回记录
    public PropertyValue findOneById(int id);

    //根据产品和属性名 查询 唯一的一条记录,主要用于检查该属性值是否已存在数据库中
    public PropertyValue findOneByPropertyAndProduct(Map<String, Object> map);

    //插入记录，一般用 空字符串 来初始化
    public void addPropertyValue(PropertyValue pv);

    //更新记录
    public void updatePropertyValue(PropertyValue pv);

}
