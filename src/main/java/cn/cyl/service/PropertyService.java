package cn.cyl.service;

import cn.cyl.entity.Category;
import cn.cyl.entity.Property;

import java.util.List;

/**
 * @author chengyl
 * @create 2018-09-15-13:21
 */
public interface PropertyService {

    //根据分类返回对应的所有属性值
    public List<Property> listByCategory(Category c);

    //插入记录
    public void addProperty(Property property);

    //删除记录
    public void deleteProperty(Property property);

    //根据id查找记录
    public Property findOneById(int id);

    //修改记录
    public void updateProperty(Property property);
}
