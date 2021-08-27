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
import beans.User;
import beans.Cart;
import beans.FoodItem;
import dto.LoginUserDTO;
import dto.RegisterUserDTO;
import dto.RestaurantQueryDTO;
import enums.Role;
import enums.Type;
import services.AdministratorService;
import services.CustomerService;
import services.RestaurantService;

public class SparkWebShopMain {

	private static RestaurantService restaurantService = new RestaurantService();
	private static AdministratorService administratorService = new AdministratorService();
	private static CustomerService customerService = new CustomerService();
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
			else if (!(customerService.checkUsername(username))) {
				return "Korisničko ime je zauzeto !";
			}
			else {
				LocalDate dayOfBirth = customerService.adjustDate(birthDate);
				int id = customerService.getCustomers().size() + 1;
				User newUser = new User(username, password, name, lastname, dayOfBirth, Role.CUSTOMER, id);
				ArrayList <FoodItem> items = new ArrayList<FoodItem>();
				Cart cart = new Cart(items, 1, 0);
				Customer newCustomer = new Customer(newUser, Type.BRONZE, 0, false, address, cart);
				customerService.registerCustomer(newCustomer);
				System.out.println(customerService.getCustomers());
				return "Uspešno ste se registrovali.";
			}	
		});
	}


}
