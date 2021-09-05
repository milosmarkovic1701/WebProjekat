package services;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Administrator;
import beans.Customer;
import beans.Manager;
import beans.Restaurant;
import beans.User;
import dto.LoginUserDTO;
import enums.Role;


public class ManagerService {
	
	ArrayList<Manager> managers = new ArrayList<Manager>();
	private HashMap<Integer, Restaurant> restaurants = new HashMap<Integer, Restaurant>();
	RestaurantService restaurantService = new RestaurantService();
	
	public ManagerService() {
		managers = getAllManagers();
	}
	
	public ArrayList<Manager> getManagers() {
		ArrayList<Manager> validManagers = new ArrayList<Manager>();
		for (Manager m : managers)
			if (!m.getUser().isDeleted())
				validManagers.add(m);
		Collections.reverse(validManagers);
		return validManagers;
	}

	public void setManagers(ArrayList<Manager> managers) {
		this.managers = managers;
	}
	
	public void addManager(Manager manager) {
		managers.add(manager);
		saveAllManagers();
	}
	
	public boolean checkUsername(String username) {
		managers = getAllManagers();
		for (Manager manager : managers) {
			if (manager.getUser().getUsername().equalsIgnoreCase(username))
				return false;
		}
		return true;
	}
	
	public ArrayList<Manager> getAllManagers() {
		Gson gson = new Gson();
		managers.clear();
		try {
			Reader reader = Files.newBufferedReader(Paths.get("./static/data/managers.json"));
			Manager[] managersList = gson.fromJson(reader, Manager[].class);
			if(managersList != null) {
			    for (int i = 0; i < managersList.length; i++) {
			        managers.add(managersList[i]);
			    }
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return managers;
	}
	
	public void saveAllManagers() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			Writer writer = Files.newBufferedWriter(Paths.get("./static/data/managers.json"));
			writer.append(gson.toJson(managers));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Manager login(LoginUserDTO user) {
		managers = getAllManagers();
		for (Manager manager : managers) {
			if (user.getUsernameLogin().equals(manager.getUser().getUsername()) && user.getPasswordLogin().equals(manager.getUser().getPassword()))
				return manager;
		}
		return null;
	}
	public Restaurant getManagedRestaurant(int id) {
			restaurants = restaurantService.getAllRestaurants();
			Restaurant ManagedRestaurant = null;
			for(Restaurant r: restaurants.values()) {
				if(r.getManagerId()==id) {
					ManagedRestaurant = r;
				}
			}
		return ManagedRestaurant;
	}
	
	
}
