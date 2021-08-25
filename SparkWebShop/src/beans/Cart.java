package beans;

import java.util.ArrayList;

public class Cart {
	
	private int id;
	private ArrayList <FoodItem> items;
	private int customerId;
	private double price;
	
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
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Cart(int id, ArrayList<FoodItem> items, int customerId, double price) {
		super();
		this.id = id;
		this.items = items;
		this.customerId = customerId;
		this.price = price;
	}
	
	
}
