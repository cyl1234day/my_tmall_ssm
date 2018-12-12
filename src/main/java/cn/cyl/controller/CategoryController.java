package cn.cyl.controller;

import cn.cyl.entity.Category;
import cn.cyl.service.CategoryService;
import cn.cyl.util.ImageUtil;
import cn.cyl.util.Page;
import cn.cyl.util.UploadImageFile;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author chengyl
 * @create 2018-09-14-16:51
 */
@Controller
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    //前端不能使用  对象.属性  的方式来传递参数
    @RequestMapping("admin_category_list")
    public String listByPage(Model model, Page page) {
        if(page == null) {
            page = new Page(0, 5);
        }
        //设置分页参数
        PageHelper.offsetPage(page.getStart(), page.getCountPerPage());
        //可以直接使用查询所有，会自动设置分页参数
        List<Category> list = categoryService.listAll();
        //获取总的记录数
        long total = new PageInfo<>(list).getTotal();
        page.setTotal(total);

        //List<Category> list = categoryService.listByPage(page);
        //设置总记录数
        //page.setTotal(categoryService.totalNumber());

        model.addAttribute("categorys", list);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }


    @RequestMapping("admin_category_add")
    public String addCategory(Category category, UploadImageFile image, HttpSession session) throws IllegalStateException, IOException {
        //如果前端的name和category中的属性名一致，则能成功传入

        //一开始的category是没有id值的，所以在编写映射文件的时候加上一句select LAST_INSERT_ID()。
        categoryService.addCategory(category);
        //imageFolder表示图片将要存放的文件夹
        File imageFolder= new File(session.getServletContext().getRealPath("img/category"));
        //file表示要存放的图片：父目录+文件名
        File file = new File(imageFolder,category.getId()+".jpg");
        //如果文件不存在，则创建
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        //把内存中的图片写入磁盘
        image.getImage().transferTo(file);
        //把文件格式转成jpg
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
        //重定向到list页面
        return "redirect:/admin_category_list";
    }


    @RequestMapping("admin_category_delete")
    public String deleteCategory(Category category, HttpSession session) {
        categoryService.deleteCategory(category);

        //同时也要删除图片
        File deletePic = new File(session.getServletContext().getRealPath("img/category/" + category.getId() + ".jpg"));
        if(deletePic.exists()){
            deletePic.delete();
        }
        return "redirect:/admin_category_list";
    }


    /**
     * 执行页面跳转
     */
    @RequestMapping("admin_category_edit")
    public String editCategory(int id, Model model) {
        Category c = categoryService.findOneById(id);
        model.addAttribute("category", c);
        return "admin/editCategory";

    }

    /**
     * 执行数据库更新操作
     * @param category
     * @return
     * @throws IOException
     * @throws IllegalStateException
     */
    @RequestMapping("admin_category_update")
    public String updateCategory(Category category, UploadImageFile image, HttpSession session) throws IllegalStateException, IOException {

        categoryService.updateCategory(category);
        //不一定会修改图片.比较奇怪的是即使没有图片上传，image也不为null
        if(image.getImage().getSize() != 0) {
            //获取图片所在的文件夹
            File imageFold = new File(session.getServletContext().getRealPath("img/category"));

            //删除原来的旧图片
            File oldFile = new File(imageFold, category.getId()+".jpg");
            oldFile.delete();
            //使用同样的名字命名新文件
            image.getImage().transferTo(oldFile);
            //把文件格式转成jpg
            BufferedImage img = ImageUtil.change2jpg(oldFile);
            ImageIO.write(img, "jpg", oldFile);
        }
        return "redirect:/admin_category_list";
    }
}
