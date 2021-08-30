package beans;

import enums.Type;

public class Customer {
	
	private User user;
	private Type type = Type.BRONZE; //bronze, silver, gold
	private int cancels = 0;
	private boolean blocked = false;
	private String address;
	private int points = 10;
	private Cart cart = new Cart();
	
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
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
	
	public Customer(User user, String address) {
		super();
		this.user = user;
		this.address = address;
	}
}
