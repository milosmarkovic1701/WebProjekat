package dto;

import beans.Cart;

public class NewOrderDTO {

	private int customerId;
	private Cart cart;
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
	public NewOrderDTO(int customerId, Cart cart) {
		super();
		this.customerId = customerId;
		this.cart = cart;
	}
	
	
}
