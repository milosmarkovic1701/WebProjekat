package services;

import java.util.ArrayList;

import beans.FoodItem;

public class FoodItemService {

	ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();
	
	public FoodItemService() {
		super();
		foodItems.add(new FoodItem("Burgir", 250, 3, "250g", "Najjaci burgir", "", 1));
		foodItems.add(new FoodItem("Burger", 250, 2, "220g", "Najjaci burger", "", 1));
		foodItems.add(new FoodItem("Pica", 250, 2, "220g", "Najjaca pica", "", 1));
		foodItems.add(new FoodItem("Ciz", 220, 1, "240g", "Najjaci ciz", "", 1));
		foodItems.add(new FoodItem("Hot dog", 150, 4, "150g", "Najjaci dog", "", 1));
		foodItems.add(new FoodItem("Sammich", 250, 5, "220g", "Najjaci sammich", "", 1));
		foodItems.add(new FoodItem("Burg", 250, 6, "220g", "Najjaci burg", "", 1));
	}

	public ArrayList<FoodItem> getFoodItems() {
		return foodItems;
	}
	
	

	public void setFoodItems(ArrayList<FoodItem> foodItems) {
		this.foodItems = foodItems;
	}

	public ArrayList<FoodItem> getRestaurantFoodItems(int restaurantId) {
		ArrayList<FoodItem> restaurantFoodItems = new ArrayList<FoodItem>();
		for (FoodItem fi : foodItems) {
			if (fi.getRestaurantId() == restaurantId)
				restaurantFoodItems.add(fi);
		}
		return restaurantFoodItems;
	}
}
