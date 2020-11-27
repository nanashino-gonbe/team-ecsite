package jp.co.internous.pancake.model.domain.dto;

import java.sql.Timestamp;

import jp.co.internous.pancake.model.domain.MstProduct;
import jp.co.internous.pancake.model.domain.TblCart;

//カート画面に表示する内容を格納するクラス。
public class CartDto {

	private int id;
	/* TblCart.id */
	private String productName;
	/* MstProduct.product_name */
	private String imageFullPath;
	/* MstProduct.image_full_path */
	private int price;
	/* MstProduct.price */
	private int productCount;
	/* TblCart.product_count */
	private int subTotal;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	public CartDto() {}
	
	public CartDto(TblCart tblCart, MstProduct mstProduct) {
		this.setId(tblCart.getId());
		this.setImageFullPath(mstProduct.getImageFullPath());
		this.setProductName(mstProduct.getProductName());
		this.setPrice(mstProduct.getPrice());
		this.setProductCount(tblCart.getProductCount());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getImageFullPath() {
		return imageFullPath;
	}
	
	public void setImageFullPath(String imageFullPath) {
		this.imageFullPath = imageFullPath;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	
	public int getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(int subtotal) {
		this.subTotal = subtotal;
	}
	
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
}
