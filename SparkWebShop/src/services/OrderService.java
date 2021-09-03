package services;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Administrator;
import beans.FoodItem;
import beans.Restaurant;
import beans.Order;
import dto.OrderQueryDTO;
import dto.UserDTO;
import enums.OrderStatus;

public class OrderService {

	private ArrayList<Order> orders = new ArrayList<Order>();
	private static RestaurantService restaurantService = new RestaurantService();

	public ArrayList<Order> getOrders() {
		return orders;
	}
	
	public ArrayList<Order> getNotRatedOrders() {
		ArrayList<Order> NROrders = new ArrayList<Order>();
		for (Order o : orders) {
			if (o.getRating() == 0 && o.getStatus() == OrderStatus.DELIVERED)
				NROrders.add(o);
		}
		return NROrders;
	}
	
	public ArrayList<Order> getNotDeliveredOrders() { 
		ArrayList<Order> NDOrders = new ArrayList<Order>();
		for (Order o : orders) {
			if (o.getStatus() != OrderStatus.DELIVERED)
				NDOrders.add(o);
		}
		return NDOrders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	public OrderService() {
		super();
		ArrayList<FoodItem> food = new ArrayList<FoodItem>();
		food.add(new FoodItem(1, "Burgir",300, 2, "250 g", "pljeskavica 250g, pavlaka, kecap", "restaurant logos" + File.separator + "gyros master.jpg", 2));
		food.add(new FoodItem(2, "Pica",700, 1, "250 g", "pelat, sampinjoni, kecap, sunka, sir", "restaurant logos" + File.separator + "balans.jpg", 2));
		orders.add(new Order(1, food, food.get(0).getRestaurantId(), LocalDateTime.now(), 100, 2, 10, OrderStatus.PROCESSING));
		orders.add(new Order(2, food, food.get(1).getRestaurantId(), LocalDateTime.now().minusDays(2), 100, 3, 10, OrderStatus.PROCESSING));
		orders.add(new Order(3, food, food.get(0).getRestaurantId(), LocalDateTime.now().plusDays(1), 200, 2, 10, OrderStatus.PREPARING));
		orders.add(new Order(4, food, food.get(1).getRestaurantId(), LocalDateTime.now().minusDays(1), 300, 2, 10, OrderStatus.READY_TO_DELIVER));
		orders.add(new Order(5, food, food.get(0).getRestaurantId(), LocalDateTime.now().minusMinutes(43), 400, 2, 10, OrderStatus.IN_TRANSPORT));
		orders.add(new Order(6, food, food.get(1).getRestaurantId(), LocalDateTime.now().minusMonths(3), 500, 2, 10, OrderStatus.DELIVERED));
		orders.add(new Order(7, food, food.get(0).getRestaurantId(), LocalDateTime.now(), 600, 2, 10, OrderStatus.CANCELLED));
		orders.add(new Order(8, food, food.get(0).getRestaurantId(), LocalDateTime.now(), 100, 2, 40, OrderStatus.PROCESSING));
		orders.add(new Order(9, food, food.get(1).getRestaurantId(), LocalDateTime.now(), 100, 3, 40, OrderStatus.PROCESSING));
		orders.add(new Order(10, food, food.get(0).getRestaurantId(), LocalDateTime.now(), 200, 2, 40, OrderStatus.PREPARING));
		orders.add(new Order(11, food, food.get(0).getRestaurantId(), LocalDateTime.now(), 300, 2, 40, OrderStatus.READY_TO_DELIVER));
		orders.add(new Order(12, food, food.get(1).getRestaurantId(), LocalDateTime.now(), 400, 2, 40, OrderStatus.IN_TRANSPORT));
		orders.add(new Order(13, food, food.get(0).getRestaurantId(), LocalDateTime.now(), 500, 2, 40, OrderStatus.DELIVERED));
		orders.add(new Order(14, food, food.get(0).getRestaurantId(), LocalDateTime.now(), 600, 2, 40, OrderStatus.CANCELLED));
	}
	
	public LocalDateTime adjustDate(String date) {
		String Date[] = date.split("-");
		int year = Integer.parseInt(Date[0]);
		int month = Integer.parseInt(Date[1]);
		int day = Integer.parseInt(Date[2]);
		return LocalDateTime.of(year, month, day, 0, 0);
	}
	
	public ArrayList<Order> searchOrders (OrderQueryDTO query) {
		ArrayList<Order> allOrders = (ArrayList<Order>)orders.clone();
		int indexCounter = -1;
		for (Order order : orders) {
			indexCounter++;
			boolean valid = true;
			if (!(query.getRestaurantName().trim().equalsIgnoreCase("")) && !(order.getRestaurantName().toLowerCase().contains(query.getRestaurantName().trim().toLowerCase()))) {
				valid = false;
			}
			if (!query.getPriceDown().equalsIgnoreCase("")) {
				if (order.getPrice() < Double.parseDouble(query.getPriceDown()))
				valid = false;
			}
			if (!query.getPriceUp().equalsIgnoreCase("")) {
				if (order.getPrice() > Double.parseDouble(query.getPriceUp()))
				valid = false;
			}
			if (!(query.getDateDown().trim().equalsIgnoreCase(""))) {
				LocalDateTime queryDateDown = adjustDate(query.getDateDown());
				if (order.getOrderDateTime().compareTo(queryDateDown) < 0)
					valid = false;
			}
			if (!(query.getDateUp().trim().equalsIgnoreCase(""))) {
				LocalDateTime queryDateUp = adjustDate(query.getDateUp());
				if (order.getOrderDateTime().compareTo(queryDateUp) > 0)
					valid = false;
			}
			if (!(query.getFilterType().trim().equalsIgnoreCase("")) && !(order.getRestaurantType().toLowerCase().contains(query.getFilterType().trim().toLowerCase()))) {
				valid = false;
			}
			if (!(query.getFilterStatus().trim().equalsIgnoreCase("")) && !(order.getStatus().toString().toLowerCase().contains(query.getFilterStatus().trim().toLowerCase()))) {
				valid = false;
			}
			if (!valid) {
				allOrders.remove(indexCounter--);
			}
		}
		ArrayList<Order> sortedOrders = allOrders;
		if (!query.getSort().equalsIgnoreCase("")) {
			if (query.getSort().equalsIgnoreCase("restoran_rastuce"))
				Collections.sort(sortedOrders, Comparator.comparing(Order::getRestaurantName));
			else if (query.getSort().equalsIgnoreCase("restoran_opadajuce"))
				Collections.sort(sortedOrders, Comparator.comparing(Order::getRestaurantName).reversed());
			else if (query.getSort().equalsIgnoreCase("cena_rastuce"))
				Collections.sort(sortedOrders, Comparator.comparing(Order::getPrice));
			else if (query.getSort().equalsIgnoreCase("cena_opadajuce"))
				Collections.sort(sortedOrders, Comparator.comparing(Order::getPrice).reversed());
			else if (query.getSort().equalsIgnoreCase("datum_rastuce"))
				Collections.sort(sortedOrders, Comparator.comparing(Order::getOrderDateTime));
			else if (query.getSort().equalsIgnoreCase("datum_opadajuce"))
				Collections.sort(sortedOrders, Comparator.comparing(Order::getOrderDateTime).reversed());
			}
		return sortedOrders;
	}
	
	public ArrayList<Order> getAllOrders() {
		Gson gson = new Gson();
		orders.clear();
		try {
			Reader reader = Files.newBufferedReader(Paths.get("./static/data/orders.json"));
			Order[] ordersList = gson.fromJson(reader, Order[].class);
			if(ordersList != null) {
			    for (int i = 0; i < ordersList.length; i++) {
			    	//if(list[i].isObrisana()) continue;
			        orders.add(ordersList[i]);
			    }
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return orders;
	}
	
	public void saveAllOrders() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			Writer writer = Files.newBufferedWriter(Paths.get("./static/data/orders.json"));
			writer.append(gson.toJson(orders));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
