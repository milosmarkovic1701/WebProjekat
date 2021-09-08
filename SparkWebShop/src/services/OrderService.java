package services;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import beans.Order;
import dto.ApproveDTO;
import dto.OrderQueryDTO;
import dto.OrderQueryDTOForRestaurant;
import beans.Administrator;
import beans.Customer;
import beans.FoodItem;
import beans.Restaurant;
import dto.RestaurantOrderDTO;
import dto.UserDTO;
import enums.OrderStatus;
import enums.Type;


public class OrderService {

	private ArrayList<Order> orders = new ArrayList<Order>();
	private static CustomerService customerService = new CustomerService();
	private static RestaurantService restaurantService = new RestaurantService();


	public ArrayList<Order> getOrders() {
		return orders;
	}	
	
	public ArrayList<Order> getCustomerOrders(int id) {
		ArrayList<Order> COrders = new ArrayList<Order>();
		ArrayList<Order> ordersCopy = getAllOrders();
		for (Order o : ordersCopy) {
			if (o.getCustomerId() == id)
				COrders.add(o);
		}
		return COrders;
	}
	
	public ArrayList<Order> getCustomerCancellableOrders(int id) {
		ArrayList<Order> CCOrders = new ArrayList<Order>();
		ArrayList<Order> ordersCopy = getAllOrders();
		CCOrders.clear();
		for (Order o : ordersCopy) {
			if (o.getStatus() == OrderStatus.PROCESSING)
				if (o.getCustomerId() == id)
					CCOrders.add(o);
		}
		return CCOrders;
	}
	
	public ArrayList<Order> cancelOrder(int id) { 
		int customerId = 0;
		double price = 0;
		getAllOrders();
		for (Order o : orders) {
			if (o.getId() == id) {
				o.setStatus(OrderStatus.CANCELLED);
				customerId = o.getCustomerId();
				price = o.getPrice();
				break;
			}
		}
		ArrayList <Customer> customers = customerService.getAllCustomers();
		for (Customer c : customers) {
			if (c.getUser().getId() == customerId) {
				c.setCancels(c.getCancels() + 1);
				c.setPoints(c.getPoints() - (price/1000 * 133 * 4));
				if (c.getPoints() >= 3000)
					c.setType(Type.SILVER);
				else if (c.getPoints() >= 4000)
					c.setType(Type.GOLD);
				else
					c.setType(Type.BRONZE);
			}
		}
		customerService.saveAllCustomers(customers);
		saveAllOrders();
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	public OrderService() {
		super();
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

	
	public void saveAllOrders(ArrayList<Order> orderss) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			Writer writer = Files.newBufferedWriter(Paths.get("./static/data/orders.json"));
			writer.append(gson.toJson(orderss));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Order> getRestaurantOrders(int id){
		ArrayList<Order> allOrders = this.getAllOrders();
		ArrayList<Order> restaurantOrders = new ArrayList<Order>();
		for(Order o: allOrders) {
			if(o.getRestaurantId()==id)
				restaurantOrders.add(o);
		}
		return restaurantOrders;
	}
	public RestaurantOrderDTO OrderToOrderDTO(Order o) {
		String ime = customerService.getCustomerById(o.getCustomerId()).getUser().getName();
		String prezime = customerService.getCustomerById(o.getCustomerId()).getUser().getLastName();
		String adresa = customerService.getCustomerById(o.getCustomerId()).getAddress();
		String artikli = "";
		for(FoodItem fi:o.getItems()) {
			artikli = artikli + fi.getName() + ",";
		}
		artikli = artikli.substring(0,artikli.length()-1);
			RestaurantOrderDTO newOrderDTO = new RestaurantOrderDTO(o.getId(),ime,prezime,adresa,artikli,o.getOrderDateTime(),o.getPrice(),o.getStatus(),o.getDateInfo()); 
		return newOrderDTO;
	
	}
	public ArrayList<RestaurantOrderDTO> getAllRestaurantOrdersDTO(int id){
		ArrayList<Order> orders = this.getRestaurantOrders(id);
		ArrayList<RestaurantOrderDTO> ordersDTO = new ArrayList<RestaurantOrderDTO>();
		for(Order o:orders) {
			RestaurantOrderDTO oDTO = this.OrderToOrderDTO(o);
			ordersDTO.add(oDTO);
		}
		return ordersDTO;
	}

	
	public ArrayList<RestaurantOrderDTO> searchOrdersForRestaurant (OrderQueryDTOForRestaurant query) {
		ArrayList<Order> allOrders = this.getAllOrders();
		ArrayList<Order> allOrdersForRestaurant = new ArrayList<Order>();
		ArrayList<Order> toDelete = new ArrayList<Order>();
		for(Order o:allOrders) {
			if(o.getRestaurantId()==query.getRestaurantId()) {
				allOrdersForRestaurant.add(o);
			}
		}
		int indexCounter = -1;
		for (Order order : allOrdersForRestaurant) {
			indexCounter++;
			boolean valid = true;
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
			if (!(query.getFilterStatus().trim().equalsIgnoreCase("")) && !(order.getStatus().toString().toLowerCase().contains(query.getFilterStatus().trim().toLowerCase()))) {
				valid = false;
			}
			if (!valid) {
				toDelete.add(order);
				//allOrders.remove(indexCounter--);
			}
		}
		
		allOrdersForRestaurant.removeAll(toDelete);
		
		ArrayList<Order> sortedOrders = allOrdersForRestaurant;
		if (!query.getSort().equalsIgnoreCase("")) {
			 if (query.getSort().equalsIgnoreCase("cena_rastuce"))
				Collections.sort(sortedOrders, Comparator.comparing(Order::getPrice));
			else if (query.getSort().equalsIgnoreCase("cena_opadajuce"))
				Collections.sort(sortedOrders, Comparator.comparing(Order::getPrice).reversed());
			else if (query.getSort().equalsIgnoreCase("datum_rastuce"))
				Collections.sort(sortedOrders, Comparator.comparing(Order::getOrderDateTime));
			else if (query.getSort().equalsIgnoreCase("datum_opadajuce"))
				Collections.sort(sortedOrders, Comparator.comparing(Order::getOrderDateTime).reversed());
			}
		
		ArrayList<RestaurantOrderDTO> newOrders = new ArrayList<RestaurantOrderDTO>();
		for(Order o:sortedOrders) {
			RestaurantOrderDTO oDTO = this.OrderToOrderDTO(o);
			newOrders.add(oDTO);
		}
		return newOrders;
	}
	public ArrayList<RestaurantOrderDTO> changeDeliveryStatusToPreparing(ApproveDTO ap){
		ArrayList<Order> orders = this.getAllOrders();
		
		for(Order o:orders) {
			if(o.getId()==ap.getOrderId()) {
				o.setStatus(OrderStatus.PREPARING);
			}
		}
		this.saveAllOrders();
		return this.getAllRestaurantOrdersDTO(ap.getRestaurantId());
	}
	public ArrayList<RestaurantOrderDTO> changeDeliveryStatusToWaiting(ApproveDTO ap){
		ArrayList<Order> orders = this.getAllOrders();
		
		for(Order o:orders) {
			if(o.getId()==ap.getOrderId()) {
				o.setStatus(OrderStatus.READY_TO_DELIVER);
			}
		}
		this.saveAllOrders();
		return this.getAllRestaurantOrdersDTO(ap.getRestaurantId());
	}
	
}
