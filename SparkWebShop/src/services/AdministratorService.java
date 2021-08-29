package services;

import java.time.LocalDate;
import java.util.ArrayList;

import beans.Administrator;
import beans.DeliveryMan;
import beans.User;
import dto.LoginUserDTO;
import enums.Role;

public class AdministratorService {

	ArrayList<Administrator> administrators = new ArrayList<Administrator>();

	public AdministratorService() {
		super();
		administrators.add(new Administrator(new User("mihailo47", "lg", "Mihailo", "Majstorovic", LocalDate.of(1999, 3, 19), Role.ADMINISTRATOR, 1)));
	}

	public ArrayList<Administrator> getAdministrators() {
		return administrators;
	}

	public void setAdministrators(ArrayList<Administrator> administrators) {
		this.administrators = administrators;
	}
	
	public Administrator login(LoginUserDTO user) {
		for (Administrator admin : administrators) {
			if (user.getUsernameLogin().equals(admin.getUser().getUsername()) && user.getPasswordLogin().equals(admin.getUser().getPassword()))
				return admin;
		}
		return null;
	}
	
	public boolean checkUsername(String username) {
		for (Administrator admin : administrators) {
			if (admin.getUser().getUsername().equalsIgnoreCase(username))
				return false;
		}
		return true;
	}
	
}
