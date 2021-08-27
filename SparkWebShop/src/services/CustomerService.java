package services;

import java.time.LocalDate;
import java.util.ArrayList;

import beans.Administrator;
import beans.Customer;

public class CustomerService {

	ArrayList<Customer> customers = new ArrayList<Customer>();
	
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
	
	public LocalDate adjustDate(String date) {
		String Date[] = date.split("-");
		int year = Integer.parseInt(Date[0]);
		int month = Integer.parseInt(Date[1]);
		int day = Integer.parseInt(Date[2]);
		return LocalDate.of(year, month, day);
	}
}
