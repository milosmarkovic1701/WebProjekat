package beans;

import java.util.ArrayList;

public class Cart {
	
	private ArrayList <FoodItem> items;
	private int customerId;
	private double price;
	
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
	public Cart(ArrayList<FoodItem> items, int customerId, double price) {
		super();
		this.items = items;
		this.customerId = customerId;
		this.price = price;
	}
	
	
}
