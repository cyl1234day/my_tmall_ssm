package cn.cyl.controller;

import cn.cyl.entity.Category;
import cn.cyl.entity.Product;
import cn.cyl.entity.Property;
import cn.cyl.entity.PropertyValue;
import cn.cyl.service.ProductService;
import cn.cyl.service.PropertyService;
import cn.cyl.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author chengyl
 * @create 2018-09-16-0:06
 */
@Controller
public class PropertyValueController {

    @Autowired
    ProductService productService;

    @Autowired
    PropertyService propertyService;

    @Autowired
    PropertyValueService propertyValueService;

    @RequestMapping("admin_propertyValue_edit")
    public String listPropertyValue(int pid, Model model) {
        //根据pid获取 产品 对象
        Product product = productService.findOneById(pid);
        //获取所属分类
        Category c = product.getCategory();
        //根据所属分类 获取 所有属性名称
        List<Property> propertyList = propertyService.listByCategory(c);
        //初始化所有属性值
        for(Property p : propertyList) {
            propertyValueService.initValue(p, product);
        }
        //根据产品查询所有属性值
        List<PropertyValue> propertyValueList = propertyValueService.listByProduct(product);
        model.addAttribute("product", product);
        model.addAttribute("propertyValues", propertyValueList);

        return "admin/editProductValue";
    }

    /**
     * @ResponseBody 注解表示该方法的返回的结果直接写入 HTTP 响应正文（ResponseBody）中，
     * 一般在异步获取数据时使用，通常是在使用 @RequestMapping 后，返回值通常解析为跳转路径，
     * 但是加上 @Responsebody 后返回结果不会被解析为跳转路径，而是直接写入HTTP 响应正文中。
     */
    @RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String updatePropertyValue(String value, int pvid) {

        PropertyValue pv = propertyValueService.findOneById(pvid);
        pv.setValue(value);
        propertyValueService.updatePropertyValue(pv);
        return "success";
        //return "admin_propertyValue_edit?pid="+pid;
    }


}
