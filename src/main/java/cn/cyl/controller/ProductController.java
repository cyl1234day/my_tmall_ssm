package cn.cyl.controller;

import cn.cyl.entity.Category;
import cn.cyl.entity.Product;
import cn.cyl.service.CategoryService;
import cn.cyl.service.ProductImageService;
import cn.cyl.service.ProductService;
import cn.cyl.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class ProductController {
	@Autowired
	CategoryService categoryService;

	@Autowired
	ProductService productService;

	@Autowired
	ProductImageService productImageService;

	//@Autowired
	//ProductImageService productImageService;


	//注意：这里的id如果前端没有值的话，传过来就是null,如果使用int基本类型接收会出错
	@RequestMapping("admin_product_add")
	public String addProduct(Product product, @RequestParam(value="cid") Integer id, Model model) {
		//前端传递过来的product是没有id，category,和date  需要自己设置
		Category c = categoryService.findOneById(id);
		product.setCategory(c);
		Date date = new Date();
		product.setCreateDate(date);
		productService.addProduct(product);
		return "redirect:/admin_product_list?cid="+id;
	}



	@RequestMapping("admin_product_list")
	public String listByPage(int cid, Page page, Model model) {
		if(page == null) {
			page = new Page(0,5);
		}
		Category c = categoryService.findOneById(cid);
		model.addAttribute("category", c);
		//设置page的参数
		page.setParam("&cid="+cid);

		PageHelper.offsetPage(page.getStart(), page.getCountPerPage());
		List<Product> list = productService.listByCategory(c);
		//设置总数
		long totalNum = new PageInfo<>(list).getTotal();
		page.setTotal(totalNum);

		//为每个Product设置展示图片
		if(list != null) {
			for(Product p : list) {
				productImageService.setShowImage(p);
			}
		}

		model.addAttribute("products", list);
		return "admin/listProduct";
	}


	@RequestMapping("admin_product_edit")
	public String editProduct(int pid, Model model) {
		Product product = productService.findOneById(pid);
		model.addAttribute("product", product);
		return "admin/editProduct";
	}


	@RequestMapping("admin_product_update")
	public String updateProduct(Product product, int cid) {
		//传过来的商品对象没有category和date两个属性，要手动设置
		Product oldProduct = productService.findOneById(product.getId());
		product.setCategory(oldProduct.getCategory());
		product.setCreateDate(oldProduct.getCreateDate());
		productService.updateProduct(product);
		return "redirect:/admin_product_list?cid="+cid;
	}


	@RequestMapping("admin_product_delete")
	public String deleteProduct(int pid) {
		Product product = productService.findOneById(pid);
		int cid = product.getCategory().getId();
		productService.deleteProduct(product);
		return "redirect:/admin_product_list?cid="+cid;
	}
}
