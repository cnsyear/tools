package com.cnsyear.bean;

/**
 * 订单bean
 * 
 * @author jiebaobao
 *
 */
public class Order {
	private Integer id;
	private String name;
	private String price;

	public Order() {
		super();
	}

	public Order(Integer id, String name, String price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", name=" + name + ", price=" + price + "]";
	}

}
