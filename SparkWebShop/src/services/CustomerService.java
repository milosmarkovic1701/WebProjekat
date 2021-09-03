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
import beans.Restaurant;
import beans.User;
import dto.EmployeeDTO;
import dto.LoginUserDTO;
import enums.Role;
import enums.Type;

public class CustomerService {
	
	ArrayList<Customer> customers = new ArrayList<Customer>();
	
public CustomerService () {
		customers = getAllCustomers();
	}
	
	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}

	public void registerCustomer(Customer newCustomer) {
		customers = getAllCustomers();
		customers.add(newCustomer);
		saveAllCustomers();
	}	
	
	public boolean checkUsername(String username) {
		customers = getAllCustomers();
		for (Customer customer : customers) {
			if (customer.getUser().getUsername().equalsIgnoreCase(username))
				return false;
		}
		return true;
	}
	
	public Customer login(LoginUserDTO user) {
		customers = getAllCustomers();
		for (Customer customer : customers) {
			if (user.getUsernameLogin().equals(customer.getUser().getUsername()) && user.getPasswordLogin().equals(customer.getUser().getPassword()))
				return customer;
		}
		return null;
	}
	
	public ArrayList<Customer> spamCustomers() {
		customers = getAllCustomers();
		ArrayList<Customer> spam = new ArrayList<Customer>();
		for (Customer c : customers) {
			if (c.getCancels() >= 5 && !c.getUser().isDeleted())
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
		customers.clear();
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
			writer.append(gson.toJson(customers));
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
