package beans;

public class Manager {

	private User user;
	private int restaurantId;
	private boolean blocked;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	
	public Manager(User user, int restaurantId) {
		super();
		this.user = user;
		this.restaurantId = restaurantId;
		this.blocked = false;
	}
	
	
}
