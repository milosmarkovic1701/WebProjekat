package beans;

public class FoodItem {
	private int id;
	private String name;
	private double price;
	private int restaurantId;
	private String size;
	private String description;
	private String photo;
	private int amount;
	private boolean deleted;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
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
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public FoodItem(int id, String name, double price, int restaurantId, String size, String description, String photo,
			int amount) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.restaurantId = restaurantId;
		this.size = size;
		this.description = description;
		this.photo = photo;
		this.amount = amount;
		this.deleted = false;
	}
	
	public FoodItem() {
		super();
		this.name = "Giros veliki";
		this.price = 380;
		this.restaurantId = 1;
		this.size = "300 g";
		this.description = "tortilja, pomfrit, tzatziki, svinjetina";
		this.photo = "";
		this.amount = 0;
		this.deleted = false;
	}
}
