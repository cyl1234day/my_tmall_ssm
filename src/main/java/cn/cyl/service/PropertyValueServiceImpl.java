package cn.cyl.service;

import cn.cyl.entity.Product;
import cn.cyl.entity.Property;
import cn.cyl.entity.PropertyValue;
import cn.cyl.mapper.PropertyValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chengyl
 * @create 2018-09-16-0:02
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService{

    @Autowired
    PropertyValueMapper mapper;

    @Override
    public List<PropertyValue> listByProduct(Product product) {
        return mapper.listByProduct(product);
    }

    @Override
    public PropertyValue findOneById(int id) {
        return mapper.findOneById(id);
    }

    @Override
    public PropertyValue findOneByPropertyAndProduct(Property property, Product product) {
        Map<String, Object> map = new HashMap<>();
        map.put("property", property);
        map.put("product", product);
        return mapper.findOneByPropertyAndProduct(map);
    }

    @Override
    public void addPropertyValue(PropertyValue pv) {
        mapper.addPropertyValue(pv);
    }

    @Override
    public void updatePropertyValue(PropertyValue pv) {
        mapper.updatePropertyValue(pv);
    }

    @Override
    public void initValue(Property p, Product product) {
        PropertyValue pv = this.findOneByPropertyAndProduct(p,product);
        if(pv == null){
            pv = new PropertyValue();
            pv.setProduct(product);
            pv.setProperty(p);
            this.addPropertyValue(pv);
        }
    }
}
