package services;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Administrator;
import beans.Customer;
import beans.Manager;
import beans.User;
import enums.Role;


public class ManagerService {
	
	ArrayList<Manager> managers = new ArrayList<Manager>();

	public ManagerService() {
		managers.add(new Manager(new User("misa00", "misa00", "Miloš", "Marković", LocalDate.of(2000, 1, 17), Role.MANAGER, 20)));
	}
	
	public ArrayList<Manager> getManagers() {
		ArrayList<Manager> validManagers = new ArrayList<Manager>();
		return managers;
	}

	public void setManagers(ArrayList<Manager> managers) {
		this.managers = managers;
	}
	
	public void addManager(Manager manager) {
		managers.add(manager);
	}
	
	public boolean checkUsername(String username) {
		for (Manager manager : managers) {
			if (manager.getUser().getUsername().equalsIgnoreCase(username))
				return false;
		}
		return true;
	}
	
	public ArrayList<Manager> getAllManagers() {
		Gson gson = new Gson();
		
		try {
			Reader reader = Files.newBufferedReader(Paths.get("./static/data/managers.json"));
			Manager[] managersList = gson.fromJson(reader, Manager[].class);
			if(managersList != null) {
			    for (int i = 0; i < managersList.length; i++) {
			    	//if(list[i].isObrisana()) continue;
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
			writer.append(gson.toJson(managers, Manager[].class));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
