package dto;
import enums.CommentStatus;
public class AdminCommentDTO {

	private String username;
	private String name;
	private String lastname;
	private String restaurantName;
	private String content;
	private String rating;
	private CommentStatus status;
	private int orderId;
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public CommentStatus getStatus() {
		return status;
	}
	public void setStatus(CommentStatus approved) {
		this.status = approved;
	}
	public AdminCommentDTO(String username, String name, String lastname, String restaurantName, String content,
			String rating, CommentStatus status, int orderId) {
		super();
		this.username = username;
		this.name = name;
		this.lastname = lastname;
		this.restaurantName = restaurantName;
		this.content = content;
		this.rating = rating;
		this.status = status;
		this.orderId = orderId;
	}

	

	
	
}
