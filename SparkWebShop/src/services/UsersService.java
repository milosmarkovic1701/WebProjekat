package services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import beans.Customer;
import beans.Administrator;
import beans.DeliveryMan;
import beans.Manager;
import beans.Restaurant;
import beans.User;
import dto.CustomerInfoEditDTO;
import dto.EmployeeDTO;
import dto.RestaurantQueryDTO;
import dto.UserDTO;
import dto.UserInfoEditDTO;
import dto.UsersQueryDTO;
import enums.Role;

public class UsersService {

	private static AdministratorService administratorService = new AdministratorService();
	private static CustomerService customerService = new CustomerService();
	private static ManagerService managerService = new ManagerService();
	private static DeliveryManService deliveryManService = new DeliveryManService();
	ArrayList<UserDTO> users = new ArrayList<UserDTO>();
	ArrayList<UserDTO> spamUsers = new ArrayList<UserDTO>();
	
	public UsersService() {}
	
	public ArrayList<UserDTO> getAllUsers() {
		users.clear();
		getCustomers();
		getDeliveryMen();
		getManagers();
		getAdministrators();
		return users;
	}
	
	public ArrayList<UserDTO> changeBlockedUser(int id) {
		for (Customer customer : customerService.getCustomers()) {
			if (customer.getUser().getId() == id) {
				if (customer.isBlocked())
					customer.setBlocked(false);
				else
					customer.setBlocked(true);
			}
		}
		return getSpam();
	}
	
	public LocalDate adjustDate(String date) {
		String Date[] = date.split("-");
		int year = Integer.parseInt(Date[0]);
		int month = Integer.parseInt(Date[1]);
		int day = Integer.parseInt(Date[2]);
		return LocalDate.of(year, month, day);
	}
	
	public String updateCustomer(CustomerInfoEditDTO customerDTO) {
		for (Customer c: customerService.getCustomers()) {
			if (c.getUser().getId() == customerDTO.getId()) {
				c.getUser().setName(customerDTO.getName());
				c.getUser().setLastName(customerDTO.getLastname());
				c.getUser().setUsername(customerDTO.getUsername());
				c.getUser().setPassword(customerDTO.getPassword());
				c.getUser().setBirthDate(adjustDate(customerDTO.getBirthDate()));
				c.setAddress(customerDTO.getAddress());
			}
		}
		return "Vaši podaci su uspešno ažurirani.";
	}
	
	public String updateUser(UserInfoEditDTO userDTO) {
		for (DeliveryMan deliveryMan: deliveryManService.getDeliveryMen()) {
			if (deliveryMan.getUser().getId() == userDTO.getId()) {
				deliveryMan.getUser().setName(userDTO.getName());
				deliveryMan.getUser().setLastName(userDTO.getLastname());
				deliveryMan.getUser().setUsername(userDTO.getUsername());
				deliveryMan.getUser().setPassword(userDTO.getPassword());
				deliveryMan.getUser().setBirthDate(adjustDate(userDTO.getBirthDate()));
			}
		}
		for (Manager manager : managerService.getManagers()) {
			if (manager.getUser().getId() == userDTO.getId()) {
				manager.getUser().setName(userDTO.getName());
				manager.getUser().setLastName(userDTO.getLastname());
				manager.getUser().setUsername(userDTO.getUsername());
				manager.getUser().setPassword(userDTO.getPassword());
				manager.getUser().setBirthDate(adjustDate(userDTO.getBirthDate()));
			}
		}
		for (Administrator admin: administratorService.getAdministrators()) {
			if (admin.getUser().getId() == userDTO.getId()) {
				admin.getUser().setName(userDTO.getName());
				admin.getUser().setLastName(userDTO.getLastname());
				admin.getUser().setUsername(userDTO.getUsername());
				admin.getUser().setPassword(userDTO.getPassword());
				admin.getUser().setBirthDate(adjustDate(userDTO.getBirthDate()));
			}
		}
		return "Vaši podaci su uspešno ažurirani.";
	}
	
	public ArrayList<UserDTO> blockUser(int id) {
		for (Customer customer : customerService.getCustomers()) {
			if (customer.getUser().getId() == id) {
				if (customer.isBlocked())
					customer.setBlocked(false);
				else
					customer.setBlocked(true);
			}
		}
		for (DeliveryMan deliveryMan: deliveryManService.getDeliveryMen()) {
			if (deliveryMan.getUser().getId() == id) {
				if (deliveryMan.isBlocked())
					deliveryMan.setBlocked(false);
				else
					deliveryMan.setBlocked(true);
			}
		}
		for (Manager manager : managerService.getManagers()) {
			if (manager.getUser().getId() == id) {
				if (manager.isBlocked())
					manager.setBlocked(false);
				else
					manager.setBlocked(true);
			}
		}
		return getAllUsers();
	}
	
	public ArrayList<UserDTO> deleteUser(int id) {
		for (Customer customer : customerService.getCustomers()) {
			if (customer.getUser().getId() == id) {
				customer.getUser().setDeleted(true);
			}
		}
		for (DeliveryMan deliveryMan: deliveryManService.getDeliveryMen()) {
			if (deliveryMan.getUser().getId() == id) {
				deliveryMan.getUser().setDeleted(true);;
			}
		}
		for (Manager manager : managerService.getManagers()) {
			if (manager.getUser().getId() == id) {
				manager.getUser().setDeleted(true);
			}
		}
		return getAllUsers();
	}
	
	public String addManager(EmployeeDTO employee) {
		String username = employee.getUsername().trim();
		String password = employee.getPassword().trim();
		String name = employee.getName().trim();
		String lastname = employee.getLastname().trim();
		String birthDate = employee.getBirthDate().trim();
		String role = employee.getRole();
		if (username.equals("") || password.equals("") || name.equals("") || lastname.equals("") || 
			birthDate.equals("") || role.equals("")) {
			return "Niste popunili sve potrebne podatke !";
		}
		else if (!customerService.checkUsername(username) || !administratorService.checkUsername(username) || 
				 !deliveryManService.checkUsername(username) || !managerService.checkUsername(username)) { 
			return "Korisničko ime je zauzeto !";
		}
		else {
			LocalDate dayOfBirth = customerService.adjustDate(birthDate);
			int amountOfUsers = managerService.getManagers().size() +
					customerService.getCustomers().size() +
					administratorService.getAdministrators().size() +
					deliveryManService.getDeliveryMen().size();
				int id = amountOfUsers + 1;
				User newUser = new User(username, password, name, lastname, dayOfBirth, Role.MANAGER, id);
				Manager newManager = new Manager(newUser);
				managerService.getManagers().add(newManager);
				System.out.println(managerService.getManagers());
				return "Uspešno ste dodali novog menadžera.";
		}
	}
	
	public String addEmployee(EmployeeDTO employee) {
		String username = employee.getUsername().trim();
		String password = employee.getPassword().trim();
		String name = employee.getName().trim();
		String lastname = employee.getLastname().trim();
		String birthDate = employee.getBirthDate().trim();
		String role = employee.getRole();
		if (username.equals("") || password.equals("") || name.equals("") || lastname.equals("") || 
			birthDate.equals("") || role.equals("")) {
			return "Niste popunili sve potrebne podatke !";
		}
		else if (!customerService.checkUsername(username) || !administratorService.checkUsername(username) || 
				 !deliveryManService.checkUsername(username) || !managerService.checkUsername(username)) { 
			return "Korisničko ime je zauzeto !";
		}
		else {
			LocalDate dayOfBirth = customerService.adjustDate(birthDate);
			int amountOfUsers = managerService.getManagers().size() +
					customerService.getCustomers().size() +
					administratorService.getAdministrators().size() +
					deliveryManService.getDeliveryMen().size();
			int id = amountOfUsers + 1;
			if (role.equalsIgnoreCase("manager")) {
				User newUser = new User(username, password, name, lastname, dayOfBirth, Role.MANAGER, id);
				Manager newManager = new Manager(newUser);
				managerService.getManagers().add(newManager);
				System.out.println(managerService.getManagers());
			}
			else if (role.equalsIgnoreCase("deliveryman")) {
				User newUser = new User(username, password, name, lastname, dayOfBirth, Role.DELIVERYMAN, id);
				DeliveryMan newDeliveryMan = new DeliveryMan(newUser);
				deliveryManService.getDeliveryMen().add(newDeliveryMan);
				System.out.println(deliveryManService.getDeliveryMen());
			}
			return "Uspešno ste dodali novog zaposlenog.";
		}
	}
	
	public void getCustomers() {
		for (Customer customer : customerService.getCustomers()) {
			if (!customer.getUser().isDeleted()) {
			User c = customer.getUser();
			String date = String.valueOf(c.getBirthDate().getDayOfMonth()) + "." + String.valueOf(c.getBirthDate().getMonthValue()) + "." + String.valueOf(c.getBirthDate().getYear()) + ".";
			users.add(new UserDTO(String.valueOf(c.getId()), 
					              c.getUsername(), 
					              c.getName(), 
					              c.getLastName(), 
					              date, 
					              "Kupac", 
					              customer.getPoints(), 
					              customerService.customerTypeStr(customer), 
					              customer.getCancels(),
								  customer.isBlocked()));
			}
		}
	}
	
	public void getAdministrators() {
		for (Administrator admin : administratorService.getAdministrators()) {
			if (!admin.getUser().isDeleted()) {
			User a = admin.getUser();
			String date = String.valueOf(a.getBirthDate().getDayOfMonth()) + "." + String.valueOf(a.getBirthDate().getMonthValue()) + "." + String.valueOf(a.getBirthDate().getYear()) + ".";
			users.add(new UserDTO(String.valueOf(a.getId()), 
					              a.getUsername(), 
					              a.getName(), 
					              a.getLastName(), 
					              date, 
					              "Administrator", 
					              0, 
					              "/", 
					              0, 
					              false));
			}
		}
	}
	
	public void getDeliveryMen() {
		for (DeliveryMan deliveryMan: deliveryManService.getDeliveryMen()) {
			if (!deliveryMan.getUser().isDeleted()) {
			User dm = deliveryMan.getUser();
			String date = String.valueOf(dm.getBirthDate().getDayOfMonth()) + "." + String.valueOf(dm.getBirthDate().getMonthValue()) + "." + String.valueOf(dm.getBirthDate().getYear()) + ".";
			users.add(new UserDTO(String.valueOf(dm.getId()), 
					              dm.getUsername(), 
					              dm.getName(), 
					              dm.getLastName(), 
					              date, 
					              "Dostavljač", 
					              0, 
					              "/", 
					              0, 
					              deliveryMan.isBlocked()));
			}
		}
	}
	
	public void getManagers() {
		for (Manager manager : managerService.getManagers()) {
			if (!manager.getUser().isDeleted()) {
			User m = manager.getUser();
			String date = String.valueOf(m.getBirthDate().getDayOfMonth()) + "." + String.valueOf(m.getBirthDate().getMonthValue()) + "." + String.valueOf(m.getBirthDate().getYear()) + ".";
			users.add(new UserDTO(String.valueOf(m.getId()), 
					              m.getUsername(), 
					              m.getName(), 
					              m.getLastName(), 
					              date, 
					              "Menadžer", 
					              0, 
					              "/", 
					              0, 
					              manager.isBlocked()));
			}
		}
	}
	
	public ArrayList<UserDTO> getSpam() {
		spamUsers.clear();
		for (Customer customer : customerService.getCustomers()) {
			if (!customer.getUser().isDeleted()) {
				if (customer.getCancels() >= 5) {
					User c = customer.getUser();
					String date = String.valueOf(c.getBirthDate().getDayOfMonth()) + "." + String.valueOf(c.getBirthDate().getMonthValue()) + "." + String.valueOf(c.getBirthDate().getYear()) + ".";
					spamUsers.add(new UserDTO(String.valueOf(c.getId()), 
							              c.getUsername(), 
							              c.getName(), 
							              c.getLastName(), 
							              date, 
							              "Kupac", 
							              customer.getPoints(), 
							              customerService.customerTypeStr(customer), 
							              customer.getCancels(),
							              customer.isBlocked()));
				}
			}
		}
		return spamUsers;
	}
	
	public ArrayList<UserDTO> searchUsers(UsersQueryDTO query) {
		ArrayList<UserDTO> allUsers = (ArrayList<UserDTO>)users.clone();
		int indexCounter = -1;
		for (UserDTO user : users) {
			indexCounter++;
			boolean valid = true;
			if (!(query.getName().trim().equalsIgnoreCase("")) && !(user.getName().toLowerCase().contains(query.getName().trim().toLowerCase()))) {
				valid = false;
			}
			if (!(query.getUsername().trim().equalsIgnoreCase("")) && !(user.getUsername().toLowerCase().contains(query.getUsername().trim().toLowerCase()))) {
				valid = false;
			}
			if (!(query.getLastname().trim().equalsIgnoreCase("")) && !(user.getLastname().toLowerCase().contains(query.getLastname().trim().toLowerCase()))) {
				valid = false;
			}
			if (!(query.getFilterType().trim().equalsIgnoreCase("")) && !(user.getUserType().toString().toLowerCase().contains(query.getFilterType().trim().toLowerCase()))) {
				valid = false;
			}
			if (!(query.getFilterRole().trim().equalsIgnoreCase("")) && !((user.getRole().toString().toLowerCase()).contains(query.getFilterRole().trim().toLowerCase()))) {
				valid = false;
			}
			if (!valid) {
				allUsers.remove(indexCounter--);
			}
		}
		ArrayList<UserDTO> sortedUsers = allUsers;
		if (!query.getSort().equalsIgnoreCase("")) {
			if (query.getSort().equalsIgnoreCase("ime_rastuce"))
				Collections.sort(sortedUsers, Comparator.comparing(UserDTO::getName));
			else if (query.getSort().equalsIgnoreCase("ime_opadajuce"))
				Collections.sort(sortedUsers, Comparator.comparing(UserDTO::getName).reversed());
			else if (query.getSort().equalsIgnoreCase("prezime_rastuce"))
				Collections.sort(sortedUsers, Comparator.comparing(UserDTO::getLastname));
			else if (query.getSort().equalsIgnoreCase("prezime_opadajuce"))
				Collections.sort(sortedUsers, Comparator.comparing(UserDTO::getLastname).reversed());
			else if (query.getSort().equalsIgnoreCase("korisnicko_rastuce"))
				Collections.sort(sortedUsers, Comparator.comparing(UserDTO::getUsername));
			else if (query.getSort().equalsIgnoreCase("korisnicko_opadajuce"))
				Collections.sort(sortedUsers, Comparator.comparing(UserDTO::getUsername).reversed());
			else if (query.getSort().equalsIgnoreCase("bodovi_rastuce"))
				Collections.sort(sortedUsers, Comparator.comparing(UserDTO::getPoints));
			else
				Collections.sort(sortedUsers, Comparator.comparing(UserDTO::getPoints).reversed());
		}
		return sortedUsers;
	}
}
