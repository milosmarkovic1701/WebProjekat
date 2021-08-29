package services;

import java.util.ArrayList;

import beans.Customer;
import beans.Administrator;
import beans.DeliveryMan;
import beans.Manager;
import beans.User;
import dto.UserDTO;

public class UsersService {

	private static AdministratorService administratorService = new AdministratorService();
	private static CustomerService customerService = new CustomerService();
	private static ManagerService managerService = new ManagerService();
	private static DeliveryManService deliveryManService = new DeliveryManService();
	ArrayList<UserDTO> users = new ArrayList<UserDTO>();
	
	public UsersService() {}
	
	public ArrayList<UserDTO> getAllUsers() {
		users.clear();
		getCustomers();
		getDeliveryMen();
		getManagers();
		getAdministrators();
		return users;
	}
	
	public void getCustomers() {
		for (Customer customer : customerService.customers) {
			User c = customer.getUser();
			String date = String.valueOf(c.getBirthDate().getDayOfMonth()) + "." + String.valueOf(c.getBirthDate().getMonthValue()) + "." + String.valueOf(c.getBirthDate().getYear());
			users.add(new UserDTO(String.valueOf(c.getId()), 
					              c.getUsername(), 
					              c.getName(), 
					              c.getLastName(), 
					              date, 
					              "Kupac", 
					              String.valueOf(customer.getPoints()), 
					              customerService.customerTypeStr(customer), 
					              String.valueOf(customer.getCancels())));
		}
	}
	
	public void getAdministrators() {
		for (Administrator admin : administratorService.getAdministrators()) {
			User a = admin.getUser();
			String date = String.valueOf(a.getBirthDate().getDayOfMonth()) + "." + String.valueOf(a.getBirthDate().getMonthValue()) + "." + String.valueOf(a.getBirthDate().getYear());
			users.add(new UserDTO(String.valueOf(a.getId()), 
					              a.getUsername(), 
					              a.getName(), 
					              a.getLastName(), 
					              date, 
					              "Administrator", 
					              "/", 
					              "/", 
					              "/"));
		}
	}
	
	public void getDeliveryMen() {
		for (DeliveryMan deliveryMan: deliveryManService.getDeliveryMen()) {
			User dm = deliveryMan.getUser();
			String date = String.valueOf(dm.getBirthDate().getDayOfMonth()) + "." + String.valueOf(dm.getBirthDate().getMonthValue()) + "." + String.valueOf(dm.getBirthDate().getYear()) + ".";
			users.add(new UserDTO(String.valueOf(dm.getId()), 
					              dm.getUsername(), 
					              dm.getName(), 
					              dm.getLastName(), 
					              date, 
					              "Dostavljač", 
					              "/", 
					              "/", 
					              "/"));
		}
	}
	
	public void getManagers() {
		for (Manager manager : managerService.getManagers()) {
			User m = manager.getUser();
			String date = String.valueOf(m.getBirthDate().getDayOfMonth()) + "." + String.valueOf(m.getBirthDate().getMonthValue()) + "." + String.valueOf(m.getBirthDate().getYear());
			users.add(new UserDTO(String.valueOf(m.getId()), 
					              m.getUsername(), 
					              m.getName(), 
					              m.getLastName(), 
					              date, 
					              "Menadžer", 
					              "/", 
					              "/", 
					              "/"));
		}
	}
}
