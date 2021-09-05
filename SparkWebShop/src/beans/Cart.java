package beans;

import java.util.ArrayList;

public class Cart {
	
	private ArrayList <FoodItem> items;
	private double price;
	private double discountPrice;
	
	public double getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(double discountPrice) {
		this.discountPrice = discountPrice;
	}
	public ArrayList<FoodItem> getItems() {
		return items;
	}
	public void setItems(ArrayList<FoodItem> items) {
		this.items = items;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public Cart() {
		items = new ArrayList<FoodItem>();
		price = 0;
		discountPrice = 0;
	}
	
	
}
