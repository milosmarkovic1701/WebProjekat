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
import beans.Restaurant;
import dto.EmployeeDTO;
import dto.LoginUserDTO;
import dto.OrderQueryDTO;
import dto.RegisterUserDTO;
import dto.RestaurantQueryDTO;
import dto.UsersQueryDTO;
import dto.AdminCommentDTO;
import enums.Role;
import enums.Type;
import services.AdministratorService;
import services.CommentService;
import services.CustomerService;
import services.DeliveryManService;
import services.FoodItemService;
import services.ManagerService;
import services.OrderService;
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
	private static OrderService orderService = new OrderService();
	private static FoodItemService foodItemService = new FoodItemService();
	private static Gson g = new Gson();

	public static void main(String[] args) throws Exception {
		port(8080);
		
		staticFiles.externalLocation(new File("./static").getCanonicalPath()); 
		
		get("/rest/restaurants/getRestaurants", (req, res) -> {
			res.type("application/json");
			return g.toJson(restaurantService.getRestaurants());
		});
		
		get("/rest/restaurants/getSelectedRestaurant/:id", (req, res) -> {
			String idS = req.params("id");
			int id = Integer.parseInt(idS);;
			res.type("application/json");
			return g.toJson(restaurantService.getRestaurant(id));
		});
		
		get("/rest/restaurants/getFoodItems/:id", (req, res) -> {
			String idS = req.params("id");
			int id = Integer.parseInt(idS);
			res.type("application/json");
			return g.toJson(foodItemService.getRestaurantFoodItems(id));
		});
		
		post("/rest/restaurants/searchRestaurants", (req, res) -> {
			RestaurantQueryDTO query = g.fromJson(req.body(), RestaurantQueryDTO.class);
			res.type("application/json");		
			return g.toJson(restaurantService.searchRestaurants(query));
		});
		
		post("/rest/restaurants/addRestaurant", (req, res) -> {
			res.type("application/json");		
			return g.toJson(restaurantService.getRestaurants());
		});
		
		post("/rest/restaurants/deleteRestaurant", (req, res) -> {
			String data = g.fromJson(req.body(), String.class);
			int restaurantId = Integer.parseInt(data);
			res.type("application/json");		
			return g.toJson(restaurantService.deleteRestaurant(restaurantId));
		});
		
		get("/rest/users/getUsers", (req, res) -> {
			res.type("application/json");
			return g.toJson(usersService.getAllUsers());
		});
		
		get("/rest/users/getManagers", (req, res) -> {
			res.type("application/json");
			return g.toJson(managerService.getManagers());
		});
		
		get("/rest/users/getSpamUsers", (req, res) -> {
			res.type("application/json");
			return g.toJson(usersService.getSpam());
		});
		
		post("/rest/users/searchUsers", (req, res) -> {
			UsersQueryDTO query = g.fromJson(req.body(), UsersQueryDTO.class);
			res.type("application/json");		
			return g.toJson(usersService.searchUsers(query));
		});
		
		post("/rest/users/deleteSelectedUser", (req, res) -> {
			String data = g.fromJson(req.body(), String.class);
			int userId = Integer.parseInt(data);
			res.type("application/json");		
			return g.toJson(usersService.deleteUser(userId));
		});
		
		post("/rest/users/blockSelectedUser", (req, res) -> {
			String data = g.fromJson(req.body(), String.class);
			int userId = Integer.parseInt(data);
			res.type("application/json");		
			return g.toJson(usersService.blockUser(userId));
		});
		
		post("/rest/users/changeBlockedUser", (req, res) -> {
			String data = g.fromJson(req.body(), String.class);
			int userId = Integer.parseInt(data);
			res.type("application/json");		
			return g.toJson(usersService.changeBlockedUser(userId));
		});
		
		post("/rest/users/loginAdministrator", (req, res) -> {
			LoginUserDTO user = g.fromJson(req.body(), LoginUserDTO.class);
			Administrator admin = administratorService.login(user);
			if (admin == null || admin.getUser().isDeleted()) 
				return "Prijava neuspešna. Proverite korisničko ime i lozinku.";
			return g.toJson(admin);
		});
		
		post("/rest/users/loginCustomer", (req, res) -> {
			LoginUserDTO user = g.fromJson(req.body(), LoginUserDTO.class);
			Customer customer = customerService.login(user);
			if (customer == null || customer.getUser().isDeleted()) 
				return "Prijava neuspešna. Proverite korisničko ime i lozinku.";
			return g.toJson(customer);
		});
		
		post("/rest/users/register", (req, res) -> {
			RegisterUserDTO user = g.fromJson(req.body(), RegisterUserDTO.class);
			String username = user.getUsernameRegister().trim();
			String password = user.getPasswordRegister().trim();
			String name = user.getName().trim();
			String lastname = user.getLastname().trim();
			String birthDate = user.getBirthDate().trim();
			String address = user.getAddress().trim();
			if (username.equals("") || password.equals("") || name.equals("") || lastname.equals("") || 
				birthDate.equals("") || address.equals("")) {
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
			return usersService.addEmployee(employee);	
		});
		
		post("rest/users/addManager", (req, res) -> {
			EmployeeDTO employee = g.fromJson(req.body(), EmployeeDTO.class);	
			return usersService.addManager(employee);
		});
		
		post("/rest/orders/searchOrders", (req, res) -> {
			OrderQueryDTO query = g.fromJson(req.body(), OrderQueryDTO.class);
			res.type("application/json");		
			return g.toJson(orderService.searchOrders(query));
		});
		
		get("/rest/comments/getAllComments", (req, res) -> {
			res.type("application/json");
			ArrayList<AdminCommentDTO> coms = new ArrayList<AdminCommentDTO>();
			coms.add(new AdminCommentDTO("misa00", "Milos", "Markovic", "Balans", "Dobar.", "4.7", "DA"));
			return g.toJson(coms);
		});
		
		get("/rest/comments/getRestaurantComments/:id", (req, res) -> {
			String idS = req.params("id");
			int id = Integer.parseInt(idS);
			res.type("application/json");
			return g.toJson(commentService.getRestaurantComments(id));
		});
		
		get("/rest/orders/getOrders", (req, res) -> {
			res.type("application/json");
			return g.toJson(orderService.getOrders());
		});
		
		get("/rest/orders/getNotDeliveredOrders", (req, res) -> {
			res.type("application/json");
			return g.toJson(orderService.getNotDeliveredOrders());
		});
		
		get("/rest/orders/getNotRatedOrders", (req, res) -> {
			res.type("application/json");
			return g.toJson(orderService.getNotRatedOrders());
		});
		
	}


}
