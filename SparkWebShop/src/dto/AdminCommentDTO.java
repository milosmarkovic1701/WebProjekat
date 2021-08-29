package dto;

public class AdminCommentDTO {

	private String username;
	private String name;
	private String lastname;
	private String restaurantName;
	private String content;
	private String rating;
	private String approved;
	
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
	public String getApproved() {
		return approved;
	}
	public void setApproved(String approved) {
		this.approved = approved;
	}
	
	public AdminCommentDTO(String username, String name, String lastname, String restaurantName, String content,
			String rating, String approved) {
		super();
		this.username = username;
		this.name = name;
		this.lastname = lastname;
		this.restaurantName = restaurantName;
		this.content = content;
		this.rating = rating;
		this.approved = approved;
	}
	
	
}
