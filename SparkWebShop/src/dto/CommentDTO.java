package dto;

public class CommentDTO {

	private int customerId;
	private int restaurantId;
	private int orderId;
	private double rating;
	private String content;
	
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
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
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
	
	public CommentDTO(int customerId, int restaurantId, int orderId, double rating, String content) {
		super();
		this.customerId = customerId;
		this.restaurantId = restaurantId;
		this.orderId = orderId;
		this.rating = rating;
		this.content = content;
	}
	
	
	
}
