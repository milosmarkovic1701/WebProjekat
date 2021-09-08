package services;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Administrator;
import beans.FoodItem;
import beans.Location;
import beans.Manager;
import beans.Restaurant;
import dto.FoodItemDTO;
import enums.RestaurantStatus;

public class FoodItemService {

	ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();
	
	public FoodItemService() {
		super();
		foodItems = getAllFoodItems();
	}

	public ArrayList<FoodItem> getFoodItems() {
		return foodItems;
	}

	public void setFoodItems(ArrayList<FoodItem> foodItems) {
		this.foodItems = foodItems;
	}

	public ArrayList<FoodItem> getRestaurantFoodItems(int restaurantId) {
		foodItems = getAllFoodItems();
		ArrayList<FoodItem> restaurantFoodItems = new ArrayList<FoodItem>();
		for (FoodItem fi : foodItems) {
			if (fi.getRestaurantId() == restaurantId && !fi.isDeleted())
				restaurantFoodItems.add(fi);
		}
		return restaurantFoodItems;
	}
	
	public ArrayList<FoodItem> getAllFoodItems() {
		Gson gson = new Gson();
		foodItems.clear();
		try {
			Reader reader = Files.newBufferedReader(Paths.get("./static/data/food items.json"));
			FoodItem[] foodItemsList = gson.fromJson(reader, FoodItem[].class);
			if(foodItemsList != null) {
			    for (int i = 0; i < foodItemsList.length; i++) {
			        foodItems.add(foodItemsList[i]);
			    }
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return foodItems;
	}
	
	public void saveAllFoodItems() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			Writer writer = Files.newBufferedWriter(Paths.get("./static/data/food items.json"));
			writer.append(gson.toJson(foodItems));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<FoodItem> addFoodItem(FoodItemDTO fi){
		ArrayList<FoodItem> foodItems = getAllFoodItems();
		String[] logoPath = fi.getPhoto().split("fakepath");
		String photo = logoPath[1].substring(1);
		int id = generateFoodItemId();
		foodItems.add( 
						new FoodItem(id,fi.getName(),
								Double.parseDouble(fi.getPrice()),
								Integer.parseInt(fi.getRestaurantId()),
										fi.getSize(),
										fi.getDescription(),
										 "food images" + File.separator + photo,
										1));
		 this.saveAllFoodItems();
		 
		return getRestaurantFoodItems(Integer.parseInt(fi.getRestaurantId()));
	}
	
	public int generateFoodItemId() {
		ArrayList<FoodItem> foodItems = getAllFoodItems();
		return foodItems.size() + 1;
	}
}
