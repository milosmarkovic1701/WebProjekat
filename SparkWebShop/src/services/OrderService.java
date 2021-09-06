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

import beans.Customer;
import beans.Order;
import dto.OrderQueryDTO;
import enums.OrderStatus;
import enums.Type;


public class OrderService {

	private ArrayList<Order> orders = new ArrayList<Order>();
	private static CustomerService customerService = new CustomerService();


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
		orders = getAllOrders();
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
}
