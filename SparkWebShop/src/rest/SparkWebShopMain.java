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
import dto.RestaurantDTO;
import dto.RestaurantQueryDTO;
import dto.UserInfoEditDTO;
import dto.UsersQueryDTO;
import dto.AdminCommentDTO;
import dto.CommentDTO;
import dto.CustomerInfoEditDTO;
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
		
		
		
		get("rest/restaurant/getAllFoodItems/:id",(req, res) -> {
			String idS = req.params("id");
			int id = Integer.parseInt(idS);
			res.type("application/json");
			return g.toJson(foodItemService.getRestaurantFoodItems(id));
		});
		
		
		get("/rest/restaurants/getRestaurants", (req, res) -> {
			res.type("application/json");
			return g.toJson(restaurantService.getRestaurants());
		});
		
		get("/rest/comments/getAllCommentsForRestaurant/:id",(req,res) -> {
			String idS = req.params("id");
			int id = Integer.parseInt(idS);
			res.type("application/json");
			return g.toJson(commentService.getRestaurantComments(id));
		});
		
		get("/rest/restaurants/getSelectedRestaurant/:id", (req, res) -> {
			String idS = req.params("id");
			int id = Integer.parseInt(idS);
			res.type("application/json");
			return g.toJson(restaurantService.getRestaurant(id));
		});
		get("/rest/restaurant/ManagedRestaurant/:id", (req, res) -> {
			String idS = req.params("id");
			int id = Integer.parseInt(idS);
			res.type("application/json");
			return g.toJson(managerService.getManagedRestaurant(id));
		});
		get("/rest/orders/allRestaurantOrders/:id",(req,res) -> {
			String idS = req.params("id");
			int id = Integer.parseInt(idS);
			res.type("application/json");
			return g.toJson(orderService.getAllRestaurantOrdersDTO(id));
		
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
			RestaurantDTO newRestaurant = g.fromJson(req.body(), RestaurantDTO.class);
			String name = newRestaurant.getName().trim();
			String type = newRestaurant.getType().trim();
			String street = newRestaurant.getStreet().trim();
			String number = newRestaurant.getNumber().trim();
			String city = newRestaurant.getCity().trim();
			String logo = newRestaurant.getLogo().trim();
			String latitude = newRestaurant.getLatitude().trim();
			String longitude = newRestaurant.getLongitude().trim();
			String postalCode = newRestaurant.getPostalCode().trim();
			String managerId = newRestaurant.getManagerId().trim();
			if (name.equals("") || type.equals("") || street.equals("") || number.equals("") || city.equals("") ||
				logo.equals("") || latitude.equals("") || longitude.equals("") || postalCode.equals("") || managerId.equals("")) {
				return "Niste popunili sve potrebne podatke !";
			}
				else
				{
					RestaurantDTO restaurant = new RestaurantDTO(name, type, logo, street, number, city, latitude, longitude, postalCode, managerId);
					return restaurantService.addRestaurant(restaurant);
				}
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
		
		post("/rest/users/saveInfoEdit", (req, res) -> {
			UserInfoEditDTO query = g.fromJson(req.body(), UserInfoEditDTO.class);	
			String username = query.getUsername().trim();
			String password = query.getPassword().trim();
			String name = query.getName().trim();
			String lastname = query.getLastname().trim();
			String birthDate = query.getBirthDate().trim();
			if (username.equals("") || password.equals("") || name.equals("") || lastname.equals("") || 
				birthDate.equals("")) {
				return "Niste popunili sve potrebne podatke !";
			}
				else
				{
					UserInfoEditDTO trimmedQuery = new UserInfoEditDTO(query.getId(), username, password, name, lastname, birthDate);	
					return usersService.updateUser(trimmedQuery);
				}
		});
		
		post("/rest/users/saveInfoEditCustomer", (req, res) -> {
			CustomerInfoEditDTO query = g.fromJson(req.body(), CustomerInfoEditDTO.class);	
			String username = query.getUsername().trim();
			String password = query.getPassword().trim();
			String name = query.getName().trim();
			String lastname = query.getLastname().trim();
			String birthDate = query.getBirthDate().trim();
			String address = query.getAddress().trim();
			if (username.equals("") || password.equals("") || name.equals("") || lastname.equals("") || 
				birthDate.equals("") || address.equals("")) {
				return "Niste popunili sve potrebne podatke !";
			}
				else
				{
					CustomerInfoEditDTO trimmedQuery = new CustomerInfoEditDTO(query.getId(), username, password, name, lastname, birthDate, address);	
					return usersService.updateCustomer(trimmedQuery);
				}
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
			if (admin == null) 
				return "Prijava neuspešna. Proverite korisničko ime i lozinku.";
			return g.toJson(admin);
		});
		
		post("/rest/users/loginCustomer", (req, res) -> {
			LoginUserDTO user = g.fromJson(req.body(), LoginUserDTO.class);
			Customer customer = customerService.login(user);
			if (customer == null) 
				return "Prijava neuspešna. Proverite korisničko ime i lozinku.";
			else if (customer.getUser().isDeleted() || customer.isBlocked())
				return "Prijava neuspešna. Vaš nalog je blokiran ili obrisan.";
			return g.toJson(customer);
		});
		post("/rest/users/loginManager", (req, res) -> {
			LoginUserDTO user = g.fromJson(req.body(), LoginUserDTO.class);
			Manager manager = managerService.login(user);
			if (manager == null) 
				return "Prijava neuspešna. Proverite korisničko ime i lozinku.";
			else if (manager.getUser().isDeleted() || manager.isBlocked())
				return "Prijava neuspešna. Vaš nalog je blokiran ili obrisan.";
			return g.toJson(manager);
		});
		
		post("/rest/users/loginDeliveryman", (req, res) -> {
			LoginUserDTO user = g.fromJson(req.body(), LoginUserDTO.class);
			DeliveryMan deliveryMen = deliveryManService.login(user);
			if (deliveryMen == null) 
				return "Prijava neuspešna. Proverite korisničko ime i lozinku.";
			else if (deliveryMen.getUser().isDeleted() || deliveryMen.isBlocked())
				return "Prijava neuspešna. Vaš nalog je blokiran ili obrisan.";
			return g.toJson(deliveryMen);
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
			return g.toJson(commentService.getRestaurantComments(id)); //sredi
		});
		
		post("/rest/comments/addComment", (req, res) -> {
			CommentDTO newComment = g.fromJson(req.body(), CommentDTO.class);
			commentService.addComment(newComment);
			return "OK";
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
