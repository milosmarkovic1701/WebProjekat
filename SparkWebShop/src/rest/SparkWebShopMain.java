package rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;

import com.google.gson.Gson;

import beans.Administrator;
import dto.LoginUserDTO;
import dto.RestaurantQueryDTO;
import services.AdministratorService;
import services.RestaurantService;

public class SparkWebShopMain {

	private static RestaurantService restaurantService = new RestaurantService();
	private static AdministratorService administratorService = new AdministratorService();
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
			res.type("application/json");
			String user = req.body();
			return ("OK");
		});
	}


}
