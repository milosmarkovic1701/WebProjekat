package dto;

public class RestaurantCommentDTO {

	private String name;
	private String lastname;
	private String orderInfo;
	private String content;
	private double rating;
	
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
	public String getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public RestaurantCommentDTO(String name, String lastname, String orderInfo, String content, double rating) {
		super();
		this.name = name;
		this.lastname = lastname;
		this.orderInfo = orderInfo;
		this.content = content;
		this.rating = rating;
	}
	
	
	
}
