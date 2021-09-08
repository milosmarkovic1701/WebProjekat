package beans;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import enums.OrderStatus;
import enums.RestaurantStatus;
import services.RestaurantService;

public class Order {

	private static RestaurantService restaurantService = new RestaurantService();
	
	private int id;
	private String restaurantName; 
	private int restaurantId;
	private String restaurantLogo;
	private String restaurantType;
	private ArrayList <FoodItem> items;
	private String orderInfo;
	private LocalDateTime orderDateTime;
	private String dateInfo;
	private double price;
	private int deliveryId;
	private int customerId;
	private OrderStatus status;
	private double rating; //0-not rated, 1-5-rated
	
	public String getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	public String getDateInfo() {
		return dateInfo;
	}
	public void setDateInfo(String dateInfo) {
		this.dateInfo = dateInfo;
	}
	public String getRestaurantLogo() {
		return restaurantLogo;
	}
	public void setRestaurantLogo(String restaurantLogo) {
		this.restaurantLogo = restaurantLogo;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<FoodItem> getItems() {
		return items;
	}
	public void setItems(ArrayList<FoodItem> items) {
		this.items = items;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public LocalDateTime getOrderDateTime() {
		return orderDateTime;
	}
	public void setOrderDateTime(LocalDateTime orderDateTime) {
		this.orderDateTime = orderDateTime;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = Math.round(price * 100)/100.0;
	}
	public int getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(int deliveryId) {
		this.deliveryId = deliveryId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public String getRestaurantType() {
		return restaurantType;
	}
	public void setRestaurantType(String restaurantType) {
		this.restaurantType = restaurantType;
	}
	
	public String formatDate(LocalDateTime date) {
		String year = String.valueOf(orderDateTime.getYear());
		String month = String.valueOf(orderDateTime.getMonthValue());
		String day = String.valueOf(orderDateTime.getDayOfMonth());
		String hours = String.valueOf(orderDateTime.getHour());
		String minutes = String.valueOf(orderDateTime.getMinute());
		if(orderDateTime.getHour()<=9) {
			hours= "0" + hours; 
		}
		if(orderDateTime.getMinute()<=9) {
			minutes= "0" + minutes; 
		}
		return day + "." + month +"." + year + ".  " + hours + ":" + minutes;
	}
	
	public Order(int id, ArrayList<FoodItem> items, int restaurantId, LocalDateTime orderDateTime, double price,
			int deliveryId, int customerId, OrderStatus status) {
		super();
		this.id = id;
		this.items = items;
		this.restaurantId = restaurantId;
		this.orderDateTime = orderDateTime;
		this.price = Math.round(price * 100)/100.0;
		this.deliveryId = deliveryId;
		this.customerId = customerId;
		this.status = status;
		this.rating = 0;
		this.orderInfo = "";
		this.dateInfo = formatDate(orderDateTime);
		for (Restaurant restaurant : restaurantService.getRestaurants()) {
			if (restaurant.getId() == restaurantId) {
				this.restaurantLogo = restaurant.getLogo();
				this.restaurantName = restaurant.getName();
				this.restaurantType = restaurant.getType();
			}
		}
		for (FoodItem food : items) {
			this.orderInfo = this.orderInfo + food.getName() + " " + food.getSize() + " " + food.getAmount() + "X, ";
		}
	}
}
