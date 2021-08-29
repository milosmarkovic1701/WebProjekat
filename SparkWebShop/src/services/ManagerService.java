package services;

import java.time.LocalDate;
import java.util.ArrayList;

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
	
	
}
