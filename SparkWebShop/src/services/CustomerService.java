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
