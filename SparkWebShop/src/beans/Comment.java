package beans;

public class Comment {

	
	private int customerId;
	private int restaurantId;
	private double rating;
	private String content;
	private boolean approved;
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	public Comment(int customerId, int restaurantId, double rating, String content) {
		super();
		this.customerId = customerId;
		this.restaurantId = restaurantId;
		this.rating = rating;
		this.content = content;
		this.approved = false;
	}
}
