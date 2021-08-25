package beans;

import enums.Type;

public class Customer {
	
	private User user;
	private Type type; //bronze, silver, gold
	private int cancels;
	private boolean blocked;
	private String address;
	private Cart cart;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public int getCancels() {
		return cancels;
	}
	public void setCancels(int cancels) {
		this.cancels = cancels;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
	public Customer(User user, Type type, int cancels, boolean blocked, String address, Cart cart) {
		super();
		this.user = user;
		this.type = type;
		this.cancels = cancels;
		this.blocked = blocked;
		this.address = address;
		this.cart = cart;
	}
}
