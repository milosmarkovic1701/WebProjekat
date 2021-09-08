package beans;
import enums.CommentStatus;
public class Comment {

	
	private int customerId;
	private int restaurantId;
	private int orderId;
	private double rating;
	private String content;
	private CommentStatus status;
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
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
	
	
	public CommentStatus getStatus() {
		return status;
	}
	public void setStatus(CommentStatus approved) {
		this.status = approved;
	}
	public Comment(int customerId, int restaurantId, int orderId, double rating, String content) {
		super();
		this.customerId = customerId;
		this.restaurantId = restaurantId;
		this.orderId = orderId;
		this.rating = rating;
		this.content = content;
		this.status = CommentStatus.CEKA_ODOBRENJE;
	}
}
