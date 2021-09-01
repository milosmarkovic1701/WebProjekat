package services;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Administrator;
import beans.Customer;
import beans.Manager;
import beans.User;
import dto.EmployeeDTO;
import dto.LoginUserDTO;
import enums.Role;
import enums.Type;

public class CustomerService {
	
	ArrayList<Customer> customers = new ArrayList<Customer>();
	
public CustomerService () {
		customers.add(new Customer(new User("anag", "ana", "Ana", "Gavrilović", LocalDate.of(1999, 9, 23), Role.CUSTOMER, 10), "Andre Jovanovića 6, Šabac"));
		customers.add(new Customer(new User("micas", "mica99", "Milica", "Samardžija", LocalDate.of(1999, 5, 1), Role.CUSTOMER, 40), "Andre Jovanovića 10, Šabac"));
		customers.add(new Customer(new User("nenaa", "nena99", "Nevena", "Atić", LocalDate.of(1999, 3, 28), Role.CUSTOMER, 50), "Andre Jovanovića 50, Šabac"));
		customers.get(1).setCancels(7);
		customers.get(1).setBlocked(true);
		customers.get(0).setCancels(5);
		customers.get(1).setPoints(110);
		customers.get(2).setPoints(3500);
		customers.get(2).setType(Type.SILVER);
	}
	
	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}

	public void registerCustomer(Customer newCustomer) {
		customers.add(newCustomer);
	}	
	
	public boolean checkUsername(String username) {
		for (Customer customer : customers) {
			if (customer.getUser().getUsername().equalsIgnoreCase(username))
				return false;
		}
		return true;
	}
	
	public Customer login(LoginUserDTO user) {
		for (Customer customer : customers) {
			if (user.getUsernameLogin().equals(customer.getUser().getUsername()) && user.getPasswordLogin().equals(customer.getUser().getPassword()))
				return customer;
		}
		return null;
	}
	
	public ArrayList<Customer> spamCustomers() {
		ArrayList<Customer> spam = new ArrayList<Customer>();
		for (Customer c : customers) {
			if (c.getCancels() >= 5)
				spam.add(c);
		}
		return spam;
	}
	
	public String customerTypeStr(Customer customer) {
		switch (customer.getType()) { 
		case BRONZE :
			return "Bronzani";
		case SILVER :
			return "Srebrni";
		case GOLD :
			return "Zlatni";
		}
		return "";
	}
	
	public ArrayList<Customer> getAllCustomers() {
		Gson gson = new Gson();
		
		try {
			Reader reader = Files.newBufferedReader(Paths.get("./static/data/customers.json"));
			Customer[] customersList = gson.fromJson(reader, Customer[].class);
			if(customersList != null) {
			    for (int i = 0; i < customersList.length; i++) {
			    	//if(list[i].isObrisana()) continue;
			        customers.add(customersList[i]);
			    }
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return customers;
	}
	
	public void saveAllCustomers() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			Writer writer = Files.newBufferedWriter(Paths.get("./static/data/customers.json"));
			writer.append(gson.toJson(customers, Customer[].class));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public LocalDate adjustDate(String date) {
		String Date[] = date.split("-");
		int year = Integer.parseInt(Date[0]);
		int month = Integer.parseInt(Date[1]);
		int day = Integer.parseInt(Date[2]);
		return LocalDate.of(year, month, day);
	}
	
	
}
