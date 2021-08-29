package rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.time.LocalDate;

import com.google.gson.Gson;

import java.util.ArrayList;

import beans.Administrator;
import beans.Customer;
import beans.DeliveryMan;
import beans.User;
import beans.Cart;
import beans.FoodItem;
import beans.Manager;
import dto.EmployeeDTO;
import dto.LoginUserDTO;
import dto.RegisterUserDTO;
import dto.RestaurantQueryDTO;
import dto.AdminCommentDTO;
import enums.Role;
import enums.Type;
import services.AdministratorService;
import services.CommentService;
import services.CustomerService;
import services.DeliveryManService;
import services.ManagerService;
import services.RestaurantService;
import services.UsersService;

public class SparkWebShopMain {

	private static RestaurantService restaurantService = new RestaurantService();
	private static AdministratorService administratorService = new AdministratorService();
	private static CustomerService customerService = new CustomerService();
	private static ManagerService managerService = new ManagerService();
	private static DeliveryManService deliveryManService = new DeliveryManService();
	private static UsersService usersService = new UsersService();
	private static CommentService commentService = new CommentService();
	private static Gson g = new Gson();

	public static void main(String[] args) throws Exception {
		port(8080);
		
		staticFiles.externalLocation(new File("./static").getCanonicalPath()); 
		
		
		get("/rest/restaurants/getRestaurants", (req, res) -> {
			res.type("application/json");
			return g.toJson(restaurantService.getRestaurants().values());
		});
		
		post("/rest/restaurants/searchRestaurants", (req, res) -> {
			RestaurantQueryDTO query = g.fromJson(req.body(), RestaurantQueryDTO.class);
			res.type("application/json");		
			return g.toJson(restaurantService.searchRestaurants(query));
		});
		
		get("/rest/users/getUsers", (req, res) -> {
			res.type("application/json");
			return g.toJson(usersService.getAllUsers());
		});
		
		post("/rest/users/loginAdministrator", (req, res) -> {
			LoginUserDTO user = g.fromJson(req.body(), LoginUserDTO.class);
			Administrator admin = administratorService.login(user);
			if (admin == null || admin.getUser().isDeleted()) 
				return "Prijava neuspešna. Proverite korisničko ime i lozinku.";
			return g.toJson(admin);
		});
		
		post("/rest/users/register", (req, res) -> {
			RegisterUserDTO user = g.fromJson(req.body(), RegisterUserDTO.class);
			String username = user.getUsernameRegister().trim();
			String password = user.getPasswordRegister().trim();
			String name = user.getName().trim();
			String lastname = user.getLastname().trim();
			String birthDate = user.getBirthDate().trim();
			String gender = user.getGender().trim();
			String address = user.getAddress().trim();
			if (username.equals("") || password.equals("") || name.equals("") || lastname.equals("") || 
				birthDate.equals("") || gender.equals("") || address.equals("")) {
				return "Niste popunili sve potrebne podatke !";
			}
			else if (!customerService.checkUsername(username) || !administratorService.checkUsername(username) || 
					 !deliveryManService.checkUsername(username) || !managerService.checkUsername(username)) { 
				return "Korisničko ime je zauzeto !";
			}
			else {
				LocalDate dayOfBirth = customerService.adjustDate(birthDate);
				int id = customerService.getCustomers().size() + 1;
				User newUser = new User(username, password, name, lastname, dayOfBirth, Role.CUSTOMER, id);
				Customer newCustomer = new Customer(newUser, address);
				customerService.registerCustomer(newCustomer);
				System.out.println(customerService.getCustomers());
				return "Uspešno ste se registrovali.";
			}	
		});
		
		post("rest/users/addEmployee", (req, res) -> {
			EmployeeDTO employee = g.fromJson(req.body(), EmployeeDTO.class);
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
		});
		
		get("/rest/comments/getComments", (req, res) -> {
			res.type("application/json");
			ArrayList<AdminCommentDTO> coms = new ArrayList<AdminCommentDTO>();
			coms.add(new AdminCommentDTO("misa00", "Milos", "Markovic", "Balans", "Dobar.", "4.7", "DA"));
			return g.toJson(coms);
		});
		
	}


}
