package beans;

import java.time.LocalDate;
import java.util.ArrayList;

import enums.OrderStatus;

public class Order {

	private int id;
	private ArrayList <FoodItem> items;
	private int restaurantId;
	private LocalDate orderDateTime;
	private double price;
	private int deliveryId;
	private int customerId;
	private OrderStatus status;
	private int rating; //0-not rated, 1-5-rated
	
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
	public LocalDate getOrderDateTime() {
		return orderDateTime;
	}
	public void setOrderDateTime(LocalDate orderDateTime) {
		this.orderDateTime = orderDateTime;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
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
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public Order(int id, ArrayList<FoodItem> items, int restaurantId, LocalDate orderDateTime, double price,
			int deliveryId, int customerId, OrderStatus status) {
		super();
		this.id = id;
		this.items = items;
		this.restaurantId = restaurantId;
		this.orderDateTime = orderDateTime;
		this.price = price;
		this.deliveryId = deliveryId;
		this.customerId = customerId;
		this.status = status;
		this.rating = 0;
	}
}
