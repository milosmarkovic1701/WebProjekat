package services;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import beans.Location;
import beans.Restaurant;
import dto.RestaurantQueryDTO;
import enums.RestaurantStatus;

public class RestaurantService {

	private HashMap<Integer, Restaurant> restaurants = new HashMap<Integer, Restaurant>();

	public RestaurantService() {
		super();
		restaurants.put(1, new Restaurant("Gyros Master", "grcki", RestaurantStatus.CLOSED, "restaurant logos" + File.separator + "gyros master.jpg", 1, 5, new Location("Balzakova","3", "Novi Sad", "21000", 30, 30)));
		restaurants.put(2, new Restaurant("Balans", "palacinke", RestaurantStatus.OPENED, "restaurant logos" + File.separator + "balans.jpg", 2, 4.7, new Location("Balzakova","7", "Novi Sad", "21000", 40, 40)));
		restaurants.put(3, new Restaurant("Gyros Master", "grcki", RestaurantStatus.CLOSED, "restaurant logos" + File.separator + "gyros master.jpg", 3, 5, new Location("Balzakova","3", "Novi Sad", "21000", 30, 30)));
		restaurants.put(4, new Restaurant("Balans", "palacinke", RestaurantStatus.OPENED, "restaurant logos" + File.separator + "balans.jpg", 4, 4.7, new Location("Balzakova","7", "Novi Sad", "21000", 40, 40)));
		restaurants.put(5, new Restaurant("Gyros Master", "grcki", RestaurantStatus.CLOSED, "restaurant logos" + File.separator + "gyros master.jpg", 5, 5, new Location("Balzakova","3", "Novi Sad", "21000", 30, 30)));
		restaurants.put(6, new Restaurant("Balans", "palacinke", RestaurantStatus.OPENED, "restaurant logos" + File.separator + "balans.jpg", 6, 4.7, new Location("Balzakova","7", "Novi Sad", "21000", 40, 40)));
	}

	public HashMap<Integer, Restaurant> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(HashMap<Integer, Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	public ArrayList<Restaurant> searchRestaurants(RestaurantQueryDTO query) {
		HashMap<Integer, Restaurant> restaurantsCopy = (HashMap<Integer, Restaurant>)restaurants.clone();
		for (Restaurant rest : restaurants.values()) {
			boolean valid = true;
			if (!(query.getName().trim().equalsIgnoreCase("")) && !(rest.getName().toLowerCase().contains(query.getName().trim().toLowerCase()))) {
				valid = false;
			}
			if (!(query.getType().trim().equalsIgnoreCase("")) && !(rest.getType().toLowerCase().contains(query.getType().trim().toLowerCase()))) {
				valid = false;
			}
			if (!(query.getFilterStatus().trim().equalsIgnoreCase("")) && !(rest.getStatus().toString().toLowerCase().contains(query.getFilterStatus().trim().toLowerCase()))) {
				valid = false;
			}
			if (!(query.getFilterType().trim().equalsIgnoreCase("")) && !(rest.getType().toLowerCase().contains(query.getFilterType().trim().toLowerCase()))) {
				valid = false;
			}
			if (!(query.getLocation().trim().equalsIgnoreCase("")) && !((rest.getAddress().toLowerCase()).contains(query.getLocation().trim().toLowerCase()))) {
				valid = false;
			}
			if (!(query.getRating().trim().equalsIgnoreCase(""))) {
				try {
					if (!(Double.parseDouble(query.getRating()) <= rest.getRating())) 
						valid = false;
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				} 
			if (!valid) {
				restaurantsCopy.remove(rest.getId());
			}
		}
		ArrayList<Restaurant> sortedRestaurants = new ArrayList<>(restaurantsCopy.values());
		if (!query.getSort().equalsIgnoreCase("")) {
			if (query.getSort().equalsIgnoreCase("naziv_rastuce"))
				Collections.sort(sortedRestaurants, Comparator.comparing(Restaurant::getName));
			else if (query.getSort().equalsIgnoreCase("naziv_opadajuce"))
				Collections.sort(sortedRestaurants, Comparator.comparing(Restaurant::getName).reversed());
			else if (query.getSort().equalsIgnoreCase("lokacija_rastuce"))
				Collections.sort(sortedRestaurants, Comparator.comparing(Restaurant::getAddress));
			else if (query.getSort().equalsIgnoreCase("lokacija_opadajuce"))
				Collections.sort(sortedRestaurants, Comparator.comparing(Restaurant::getAddress).reversed());
			else if (query.getSort().equalsIgnoreCase("ocena_rastuce"))
				Collections.sort(sortedRestaurants, Comparator.comparing(Restaurant::getRating));
			else
				Collections.sort(sortedRestaurants, Comparator.comparing(Restaurant::getRating).reversed());
		}
		return sortedRestaurants;
	}
}
