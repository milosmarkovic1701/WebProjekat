package beans;

import java.util.ArrayList;

public class DeliveryMan {

	private User user;
	private boolean blocked;
	private ArrayList <Order> ordersToDeliver;
	
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
	
	public DeliveryMan(User user, boolean blocked, ArrayList<Order> ordersToDeliver) {
		super();
		this.user = user;
		this.blocked = blocked;
		this.ordersToDeliver = ordersToDeliver;
	}
	
	
}
