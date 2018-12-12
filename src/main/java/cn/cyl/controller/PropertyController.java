package cn.cyl.controller;

import cn.cyl.entity.Category;
import cn.cyl.entity.Property;
import cn.cyl.service.CategoryService;
import cn.cyl.service.PropertyService;
import cn.cyl.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author chengyl
 * @create 2018-09-15-13:26
 */
@Controller
public class PropertyController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    PropertyService propertyService;

    //注意这里使用的参数为cid，表示category的id
    @RequestMapping("admin_property_list")
    public String listByPage(int cid, Model model, Page page) {
        if(page == null) {
            page = new Page(0,5);
        }

        Category c = categoryService.findOneById(cid);
        model.addAttribute("category", c);

        PageHelper.offsetPage(page.getStart(),page.getCountPerPage());
        List<Property> list = propertyService.listByCategory(c);

        long totalCount = new PageInfo<>(list).getTotal();
        //设置总记录数
        page.setTotal(totalCount);

        //拼接url.
        /*
         * start=${status.index*page.countPerPage}${page.param}
         * ?start=5&cid=3
         */
        page.setParam("&cid="+cid);

        model.addAttribute("page", page);
        model.addAttribute("propertys",list);
        return "/admin/listProperty";
    }

    @RequestMapping("admin_property_add")
    public String addProperty(String name, int cid) {
        Category category = categoryService.findOneById(cid);
        Property property = new Property();
        property.setName(name);
        property.setCategory(category);
        propertyService.addProperty(property);
        return "redirect:/admin_property_list?cid="+cid;
    }


    @RequestMapping("admin_property_edit")
    public String editProperty(int pid, Model model) {
        Property property = propertyService.findOneById(pid);
        model.addAttribute("property", property);
        return "/admin/editProperty";
    }

    //cid主要是为了返回对应的页面用，并不能更改
    @RequestMapping("admin_property_update")
    public String updateProperty(int pid, int cid, String name) {
        Property property = propertyService.findOneById(pid);
        property.setName(name);
        propertyService.updateProperty(property);
        return "redirect:/admin_property_list?cid="+cid;
    }

    @RequestMapping("admin_property_delete")
    public String deleteProperty(int pid) {
        Property property = propertyService.findOneById(pid);
        propertyService.deleteProperty(property);
        int cid = property.getCategory().getId();
        return "redirect:/admin_property_list?cid="+cid;
    }

}
