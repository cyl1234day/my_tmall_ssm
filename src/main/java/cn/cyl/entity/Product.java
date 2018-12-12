package cn.cyl.entity;

import java.util.Date;
import java.util.List;

public class Product {

	private int id;
	private String name;
	private String subTitle;
	private double originalPrice;
	private double promotePrice;
	private int stock;
	private Date createDate;
	private Category category;

	//用来在产品列表中展示用
	private ProductImage showImage;


	//-------------前端----------------------------------------------------
	//用来存放左边的商品展示图片
	private List<ProductImage> productShowImages;

	//用来存放下面的商品详细图片
	private List<ProductImage> productDetailImages;

	//总的评价数
	private int reviewCount;

	//总销量
	private int saleCount;

//-----------------------setter and getter -------------------------
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public double getPromotePrice() {
		return promotePrice;
	}

	public void setPromotePrice(double promotePrice) {
		this.promotePrice = promotePrice;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public ProductImage getShowImage() {
		return showImage;
	}

	public void setShowImage(ProductImage showImage) {
		this.showImage = showImage;
	}

	
	public List<ProductImage> getProductShowImages() {
		return productShowImages;
	}

	public void setProductShowImages(List<ProductImage> productShowImages) {
		this.productShowImages = productShowImages;
	}

	public List<ProductImage> getProductDetailImages() {
		return productDetailImages;
	}

	public void setProductDetailImages(List<ProductImage> productDetailImages) {
		this.productDetailImages = productDetailImages;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public int getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}

	@Override
	public String toString() {
		return "Product [category=" + category + ", createDate=" + createDate + ", id=" + id + ", name=" + name
				+ ", originalPrice=" + originalPrice + ", promotePrice=" + promotePrice + ", stock=" + stock
				+ ", subTitle=" + subTitle + "]";
	}
	

}
