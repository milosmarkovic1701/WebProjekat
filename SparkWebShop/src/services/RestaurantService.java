package services;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.eclipse.jetty.websocket.common.events.ParamList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.FoodItem;
import beans.Location;
import beans.Manager;
import beans.Restaurant;
import dto.RestaurantDTO;
import dto.RestaurantQueryDTO;
import enums.RestaurantStatus;

public class RestaurantService {

	private HashMap<Integer, Restaurant> restaurants = new HashMap<Integer, Restaurant>();
	private static ManagerService managerService = new ManagerService();

	public RestaurantService() {
		restaurants = getAllRestaurants();
	}
	
	public HashMap<Integer, Restaurant> getAllRestaurants() {
		Gson gson = new Gson();
		restaurants.clear();
		try {
			Reader reader = Files.newBufferedReader(Paths.get("./static/data/restaurants.json"));
			Restaurant[] restaurantList = gson.fromJson(reader, Restaurant[].class);
			if(restaurantList != null) {
			    for (int i = 0; i < restaurantList.length; i++) {
			        restaurants.put(restaurantList[i].getId(), restaurantList[i]);
			    }
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return restaurants;
	}
	
	public void saveAllRestaurants() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			Writer writer = Files.newBufferedWriter(Paths.get("./static/data/restaurants.json"));
			writer.append(gson.toJson(restaurants.values().toArray(), Restaurant[].class));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Restaurant> getRestaurants() {
		ArrayList<Restaurant> allRestaurants = new ArrayList<>(getAllRestaurants().values());
		ArrayList<Restaurant> validRestaurants = new ArrayList<Restaurant>();
		for (Restaurant r : allRestaurants) {
			if (!r.isDeleted())
				validRestaurants.add(r);
		}
		Collections.sort(validRestaurants, Comparator.comparing(Restaurant::getStatus));
		
		return validRestaurants;
	}
	
	public int generateRestaurantId() {
		restaurants = getAllRestaurants();
		return restaurants.size() + 1;
	}
	
	public String addRestaurant(RestaurantDTO newRestaurant) {
		restaurants = getAllRestaurants();
		String[] logoPath = newRestaurant.getLogo().split("fakepath");
		String logoName = logoPath[1].substring(1);
		int id = generateRestaurantId();
		restaurants.put(id, 
						new Restaurant(newRestaurant.getName(), 
							   newRestaurant.getType(), 
						       RestaurantStatus.CLOSED, 
						       "restaurant logos" + File.separator + logoName, 
						       id, 
						       0, 
						       new Location(newRestaurant.getStreet(), 
						    		   newRestaurant.getNumber(), 
						    		   newRestaurant.getCity(), 
						    		   newRestaurant.getPostalCode(), 
						    		   Double.parseDouble(newRestaurant.getLongitude()), 
						    		   Double.parseDouble(newRestaurant.getLatitude())),
						       		   Integer.parseInt(newRestaurant.getManagerId())));
		saveAllRestaurants();
		ArrayList<Manager> managers = managerService.getAllManagers();
		for (Manager m : managers) {
			if (Integer.parseInt(newRestaurant.getManagerId()) == m.getUser().getId()) {
				m.setRestaurantId(id);
				break;
			}
		}
		managerService.saveAllManagersForRestaurant(managers);
		return "Nov restoran uspešno dodat.";
	}
	
	public Restaurant getRestaurant(int id) {
		return getAllRestaurants().get(id);
	}
	
	public ArrayList<Restaurant> deleteRestaurant(int id) {
		restaurants = getAllRestaurants();
		restaurants.get(id).setDeleted(true);
		saveAllRestaurants();
		ArrayList<Manager> managers = managerService.getAllManagers();
		for (Manager m : managers) {
			if (id == m.getRestaurantId()) {
				m.setRestaurantId(0);
				break;
			}
		}
		managerService.saveAllManagersForRestaurant(managers);
		return getRestaurants();
	}

	public void setRestaurants(HashMap<Integer, Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	public ArrayList<Restaurant> searchRestaurants(RestaurantQueryDTO query) {
		restaurants = getAllRestaurants();
		HashMap<Integer, Restaurant> restaurantsCopy = (HashMap<Integer, Restaurant>)restaurants.clone();
		for (Restaurant rest : restaurants.values()) {
			if (!rest.isDeleted()) {
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
		}
		ArrayList<Restaurant> sortedRestaurants = new ArrayList<>(restaurantsCopy.values());
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
			else if (query.getSort().equalsIgnoreCase("ocena_opadajuce"))
				Collections.sort(sortedRestaurants, Comparator.comparing(Restaurant::getRating).reversed());
			else
				Collections.sort(sortedRestaurants, Comparator.comparing(Restaurant::getStatus));
			return sortedRestaurants;
	}
}
