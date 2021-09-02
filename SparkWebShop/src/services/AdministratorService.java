package services;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Administrator;
import beans.DeliveryMan;
import beans.Restaurant;
import beans.User;
import dto.LoginUserDTO;
import enums.Role;

public class AdministratorService {

	ArrayList<Administrator> administrators = new ArrayList<Administrator>();

	public AdministratorService() {
		administrators = getAllAdministrators();
	}

	public ArrayList<Administrator> getAdministrators() {
		return administrators;
	}

	public void setAdministrators(ArrayList<Administrator> administrators) {
		this.administrators = administrators;
	}
	
	public Administrator login(LoginUserDTO user) {
		administrators = getAllAdministrators();
		for (Administrator admin : administrators) {
			if (user.getUsernameLogin().equals(admin.getUser().getUsername()) && user.getPasswordLogin().equals(admin.getUser().getPassword()))
				return admin;
		}
		return null;
	}
	
	public boolean checkUsername(String username) {
		administrators = getAllAdministrators();
		for (Administrator admin : administrators) {
			if (admin.getUser().getUsername().equalsIgnoreCase(username))
				return false;
		}
		return true;
	}
	
	public ArrayList<Administrator> getAllAdministrators() {
		Gson gson = new Gson();
		
		try {
			Reader reader = Files.newBufferedReader(Paths.get("./static/data/administrators.json"));
			Administrator[] administratorsList = gson.fromJson(reader, Administrator[].class);
			if(administratorsList != null) {
			    for (int i = 0; i < administratorsList.length; i++) {
			        administrators.add(administratorsList[i]);
			    }
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return administrators;
	}
	
	public void saveAllAdministrators() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			Writer writer = Files.newBufferedWriter(Paths.get("./static/data/administrators.json"));
			writer.append(gson.toJson(administrators));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
