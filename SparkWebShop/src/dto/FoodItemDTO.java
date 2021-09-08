package dto;

public class FoodItemDTO {

	private String name;
	private String price;
	private String restaurantId;
	private String description;
	private String photo;
	private String size;
	private String id;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getSize() {
		return size;
	}
	public void getSize(String size) {
		this.size = size;
	}
	public FoodItemDTO(String name, String price, String restaurantId, String description, String photo, String size,String id) {
		super();
		this.name = name;
		this.price = price;
		this.restaurantId = restaurantId;
		this.description = description;
		this.photo = photo;
		this.size = size;
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
