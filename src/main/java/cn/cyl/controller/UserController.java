package cn.cyl.controller;

import cn.cyl.entity.User;
import cn.cyl.service.UserService;
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
 * @create 2018-09-16-1:31
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("admin_user_list")
    public String listUser(Page page, Model model) {
        if(page == null) {
            page = new Page(0,5);
        }

        PageHelper.offsetPage(page.getStart(), page.getCountPerPage());
        List<User> list = userService.listAll();

        long totalNumber = new PageInfo<>(list).getTotal();

        page.setTotal(totalNumber);
        model.addAttribute("users", list);
        model.addAttribute("page", page);
        return "admin/listUser";
    }
}
