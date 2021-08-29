package beans;

import java.util.ArrayList;

public class DeliveryMan {

	private User user;
	private boolean blocked = false;
	private ArrayList <Order> ordersToDeliver = new ArrayList <Order>();
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public ArrayList<Order> getOrdersToDeliver() {
		return ordersToDeliver;
	}
	public void setOrdersToDeliver(ArrayList<Order> ordersToDeliver) {
		this.ordersToDeliver = ordersToDeliver;
	}
	
	public DeliveryMan(User user) {
		super();
		this.user = user;
	}
	
	
}
