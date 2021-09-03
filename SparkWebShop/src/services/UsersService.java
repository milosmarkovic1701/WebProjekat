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
		for (Customer customer : customerService.getAllCustomers()) {
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
		for (Customer c: customerService.getAllCustomers()) {
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
		for (DeliveryMan deliveryMan: deliveryManService.getAllDeliveryMen()) {
			if (deliveryMan.getUser().getId() == userDTO.getId()) {
				deliveryMan.getUser().setName(userDTO.getName());
				deliveryMan.getUser().setLastName(userDTO.getLastname());
				deliveryMan.getUser().setUsername(userDTO.getUsername());
				deliveryMan.getUser().setPassword(userDTO.getPassword());
				deliveryMan.getUser().setBirthDate(adjustDate(userDTO.getBirthDate()));
				deliveryManService.saveAllDeliveryMen();
			}
		}
		for (Manager manager : managerService.getAllManagers()) {
			if (manager.getUser().getId() == userDTO.getId()) {
				manager.getUser().setName(userDTO.getName());
				manager.getUser().setLastName(userDTO.getLastname());
				manager.getUser().setUsername(userDTO.getUsername());
				manager.getUser().setPassword(userDTO.getPassword());
				manager.getUser().setBirthDate(adjustDate(userDTO.getBirthDate()));
				managerService.saveAllManagers();
			}
		}
		for (Administrator admin: administratorService.getAllAdministrators()) {
			if (admin.getUser().getId() == userDTO.getId()) {
				admin.getUser().setName(userDTO.getName());
				admin.getUser().setLastName(userDTO.getLastname());
				admin.getUser().setUsername(userDTO.getUsername());
				admin.getUser().setPassword(userDTO.getPassword());
				admin.getUser().setBirthDate(adjustDate(userDTO.getBirthDate()));
				administratorService.saveAllAdministrators();
			}
		}
		return "Vaši podaci su uspešno ažurirani.";
	}
	
	public ArrayList<UserDTO> blockUser(int id) {
		for (Customer customer : customerService.getAllCustomers()) {
			if (customer.getUser().getId() == id) {
				if (customer.isBlocked())
					customer.setBlocked(false);
				else
					customer.setBlocked(true);
			}
		}
		customerService.saveAllCustomers();
		for (DeliveryMan deliveryMan: deliveryManService.getAllDeliveryMen()) {
			if (deliveryMan.getUser().getId() == id) {
				if (deliveryMan.isBlocked())
					deliveryMan.setBlocked(false);
				else
					deliveryMan.setBlocked(true);
			}
		}
		deliveryManService.saveAllDeliveryMen();
		for (Manager manager : managerService.getAllManagers()) {
			if (manager.getUser().getId() == id) {
				if (manager.isBlocked())
					manager.setBlocked(false);
				else
					manager.setBlocked(true);
			}
		}
		managerService.saveAllManagers();
		return getAllUsers();
	}
	
	public ArrayList<UserDTO> deleteUser(int id) {
		for (Customer customer : customerService.getAllCustomers()) {
			if (customer.getUser().getId() == id) {
				customer.getUser().setDeleted(true);
				customerService.saveAllCustomers();
			}
		}
		for (DeliveryMan deliveryMan: deliveryManService.getAllDeliveryMen()) {
			if (deliveryMan.getUser().getId() == id) {
				deliveryMan.getUser().setDeleted(true);
				deliveryManService.saveAllDeliveryMen();
			}
		}
		for (Manager manager : managerService.getAllManagers()) {
			if (manager.getUser().getId() == id) {
				manager.getUser().setDeleted(true);
				managerService.saveAllManagers();
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
			int amountOfUsers = managerService.getAllManagers().size() +
					customerService.getAllCustomers().size() +
					administratorService.getAllAdministrators().size() +
					deliveryManService.getAllDeliveryMen().size();
				int id = amountOfUsers + 1;
				User newUser = new User(username, password, name, lastname, dayOfBirth, Role.MANAGER, id);
				Manager newManager = new Manager(newUser);
				managerService.getAllManagers().add(newManager);
				managerService.saveAllManagers();
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
			int amountOfUsers = managerService.getAllManagers().size() +
					customerService.getAllCustomers().size() +
					administratorService.getAllAdministrators().size() +
					deliveryManService.getAllDeliveryMen().size();
			int id = amountOfUsers + 1;
			if (role.equalsIgnoreCase("manager")) {
				User newUser = new User(username, password, name, lastname, dayOfBirth, Role.MANAGER, id);
				Manager newManager = new Manager(newUser);
				managerService.getAllManagers().add(newManager);
				managerService.saveAllManagers();
			}
			else if (role.equalsIgnoreCase("deliveryman")) {
				User newUser = new User(username, password, name, lastname, dayOfBirth, Role.DELIVERYMAN, id);
				DeliveryMan newDeliveryMan = new DeliveryMan(newUser);
				deliveryManService.getAllDeliveryMen().add(newDeliveryMan);
				deliveryManService.saveAllDeliveryMen();
			}
			return "Uspešno ste dodali novog zaposlenog.";
		}
	}
	
	public void getCustomers() {
		ArrayList <Customer> allCustomers = customerService.getAllCustomers();
		for (Customer customer : allCustomers) {
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
		ArrayList <Administrator> allAdmins = administratorService.getAllAdministrators();
		for (Administrator admin : allAdmins) {
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
		ArrayList <DeliveryMan> allDeliveryMen = deliveryManService.getAllDeliveryMen();
		for (DeliveryMan deliveryMan: allDeliveryMen) {
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
		ArrayList <Manager> allManagers = managerService.getAllManagers();
		for (Manager manager : allManagers) {
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
		ArrayList <Customer> allCustomers = customerService.getAllCustomers();
		spamUsers.clear();
		for (Customer customer : allCustomers) {
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
		getAllUsers();
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
