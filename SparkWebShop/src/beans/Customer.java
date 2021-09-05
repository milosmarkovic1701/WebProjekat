package beans;

import java.util.ArrayList;

import enums.Type;

public class Customer {
	
	private User user;
	private Type type = Type.BRONZE; //bronze, silver, gold
	private int cancels = 0;
	private boolean blocked = false;
	private String address;
	private double points = 0;
	private Cart cart = new Cart();
	
	public double getPoints() {
		return points;
	}
	public void setPoints(double points) {
		this.points = points;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public int getCancels() {
		return cancels;
	}
	public void setCancels(int cancels) {
		this.cancels = cancels;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public Cart addToCart(FoodItem fi) {
		ArrayList<FoodItem> foodItems = cart.getItems();
		boolean exists = false;
		for (FoodItem food : foodItems) {
			if (food.getId() == fi.getId()) {
				exists = true;
				food.setAmount(food.getAmount() + fi.getAmount());
			}
		}
		if (!exists)
			foodItems.add(fi);
		cart.setItems(foodItems);
		double price = cart.getPrice() + fi.getAmount() * fi.getPrice();
		double discountPrice = cart.getDiscountPrice();
		if (getType() == Type.BRONZE)
			discountPrice += fi.getAmount() * fi.getPrice();
		else if (getType() == Type.SILVER)
			discountPrice += fi.getAmount() * fi.getPrice()*0.97;
		else
			discountPrice += fi.getAmount() * fi.getPrice()*0.95;
		cart.setPrice(price);
		cart.setDiscountPrice(discountPrice);
		return cart;
	}
	
	public Cart removeCartItem(int id, int amountToRemove) {
		ArrayList<FoodItem> foodItems = cart.getItems();
		ArrayList<FoodItem> foodItemsCopy = foodItems;
		FoodItem itemToDelete = null;
		int index = 0;
		for (int i = 0; i < foodItemsCopy.size(); i++) {
			if (foodItemsCopy.get(i).getId() == id) {
				itemToDelete = foodItemsCopy.get(i);
				index = i;
				break;
			}
		}
		foodItems.remove(index);
		cart.setItems(foodItems);
		double price = cart.getPrice() - itemToDelete.getPrice() * amountToRemove;
		double discountPrice = cart.getDiscountPrice();
		if (getType() == Type.BRONZE)
			discountPrice -= amountToRemove * itemToDelete.getPrice();
		else if (getType() == Type.SILVER)
			discountPrice -= amountToRemove * itemToDelete.getPrice()*0.97;
		else
			discountPrice -= amountToRemove * itemToDelete.getPrice()*0.95;
		cart.setPrice(price);
		cart.setDiscountPrice(discountPrice);
		return cart;
	}
	
	public Cart updateCart(int id, int newAmount) {
		int oldAmount = 0;
		FoodItem itemToEdit = null;
		ArrayList<FoodItem> foodItems = cart.getItems();
		for (FoodItem fi : foodItems) {
			if (fi.getId() == id) {
				itemToEdit = fi;
				oldAmount = fi.getAmount();
				fi.setAmount(newAmount);
			}
		}
		int amountDifference = oldAmount - newAmount;
		cart.setItems(foodItems);
		double price = cart.getPrice() - (amountDifference * itemToEdit.getPrice());
		double discountPrice = cart.getDiscountPrice();
		if (getType() == Type.BRONZE)
			discountPrice -= amountDifference * itemToEdit.getPrice();
		else if (getType() == Type.SILVER)
			discountPrice -= amountDifference * itemToEdit.getPrice()*0.97;
		else
			discountPrice -= amountDifference * itemToEdit.getPrice()*0.95;
		cart.setPrice(price);
		cart.setDiscountPrice(discountPrice);
		return cart;
	}
	
	public Customer(User user, String address) {
		super();
		this.user = user;
		this.address = address;
	}
}
