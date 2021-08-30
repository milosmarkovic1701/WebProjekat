package services;

import java.time.LocalDate;
import java.util.ArrayList;

import beans.Customer;
import beans.User;
import enums.Role;

public class CustomerService {
	
	ArrayList<Customer> customers = new ArrayList<Customer>();
	
public CustomerService () {
		customers.add(new Customer(new User("anag", "anag", "Ana", "Gavrilović", LocalDate.of(1999, 9, 23), Role.CUSTOMER, 10), "Andre Jovanovića 6, Šabac"));
		customers.add(new Customer(new User("mica6699", "mica6699", "Milica", "Samardžija", LocalDate.of(1999, 5, 1), Role.CUSTOMER, 40), "Andre Jovanovića 10, Šabac"));
		customers.get(1).setCancels(7);
		customers.get(1).setBlocked(true);
		customers.get(0).setCancels(5);
		customers.get(1).setPoints(110);
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
	
	public LocalDate adjustDate(String date) {
		String Date[] = date.split("-");
		int year = Integer.parseInt(Date[0]);
		int month = Integer.parseInt(Date[1]);
		int day = Integer.parseInt(Date[2]);
		return LocalDate.of(year, month, day);
	}
}
