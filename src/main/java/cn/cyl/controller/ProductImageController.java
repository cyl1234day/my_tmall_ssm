package cn.cyl.controller;

import cn.cyl.entity.Product;
import cn.cyl.entity.ProductImage;
import cn.cyl.service.ProductImageService;
import cn.cyl.service.ProductService;
import cn.cyl.util.ImageUtil;
import cn.cyl.util.UploadImageFile;
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
 * @create 2018-09-15-19:22
 */
@Controller
public class ProductImageController {

    @Autowired
    ProductImageService productImageService;

    @Autowired
    ProductService productService;

    /**
     *
     * @param image 前端传过来的图片实体
     * @param productImage 前端传过来的图片类型（缩略图还是详细图？属于哪个产品）
     * @param pid 产品id
     * @return
     * @throws IOException
     * @throws IllegalStateException
     */
    @RequestMapping("admin_productImage_add")
    public String addProductImage(UploadImageFile image, ProductImage productImage, int pid, HttpSession session) throws IllegalStateException, IOException {

        //查询产品
        Product product = productService.findOneById(pid);

        //保存图片信息
        productImage.setProduct(product);
        productImageService.addProductImage(productImage);
        //根据图片信息把图片存到本地磁盘
        String type = productImage.getType();
        //表示要存放图片的文件夹
        File imageFile = null;
        if(type.equals("type_show")) {
            imageFile = new File(session.getServletContext().getRealPath("img/ProductShow"));
        } else {
            imageFile = new File(session.getServletContext().getRealPath("img/ProductDetail"));
        }
        //图片的文件名
        File file = new File(imageFile, productImage.getId()+".jpg");
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        //把内存中的图片写入磁盘
        image.getImage().transferTo(file);
        //把文件格式转成jpg
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);

        return "redirect:/admin_productImage_list?pid="+pid;
    }


    @RequestMapping("admin_productImage_list")
    public String listImage(int pid, Model model) {
        Product product = productService.findOneById(pid);
        List<ProductImage> showImages =  productImageService.list(product, "type_show");
        List<ProductImage> detailImages =  productImageService.list(product, "type_detail");
        model.addAttribute("product", product);
        model.addAttribute("productShowImages", showImages);
        model.addAttribute("productDetailImages", detailImages);
        return "admin/listProductImage";
    }


    @RequestMapping("admin_productImage_delete")
    public String deleteProductImage(int id, HttpSession session) {
        //获取图片信息
        ProductImage productImage = productImageService.findOneById(id);
        //获取图片类型
        String type = productImage.getType();
        File imageFile = null;
        if(type.equals("type_show")) {
            imageFile = new File(session.getServletContext().getRealPath("img/ProductShow"));
        } else {
            imageFile = new File(session.getServletContext().getRealPath("img/ProductDetail"));
        }
        File file = new File(imageFile, id+".jpg");
        //删除图片
        if(file.exists()) {
            file.delete();
        }
        //得到产品编号，用于返回产品图片管理界面
        int pid = productImage.getProduct().getId();
        //删除数据库记录
        productImageService.deleteProductImage(productImage);

        return "redirect:/admin_productImage_list?pid="+pid;
    }


}
