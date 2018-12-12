package cn.cyl.service;

import cn.cyl.entity.Category;
import cn.cyl.entity.Property;
import cn.cyl.mapper.PropertyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chengyl
 * @create 2018-09-15-13:22
 */
@Service
public class PropertyServiceImpl implements PropertyService{

    @Autowired
    PropertyMapper mapper;

    @Override
    public List<Property> listByCategory(Category c) {
        return mapper.listByCategory(c);
    }

    @Override
    public void addProperty(Property property) {
        mapper.addProperty(property);
    }

    @Override
    public void deleteProperty(Property property) {
        mapper.deleteProperty(property);
    }

    @Override
    public Property findOneById(int id) {
        return mapper.findOneById(id);
    }

    @Override
    public void updateProperty(Property property) {
        mapper.updateProperty(property);
    }
}
