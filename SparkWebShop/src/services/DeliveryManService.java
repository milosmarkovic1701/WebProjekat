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
import beans.DeliveryMan;
import beans.Manager;
import beans.User;
import enums.Role;

public class DeliveryManService {
	
	ArrayList<DeliveryMan> deliveryMen = new ArrayList<DeliveryMan>();

	public DeliveryManService() {
		deliveryMen.add(new DeliveryMan(new User("pera99", "pera99", "Petar", "Marković", LocalDate.of(1999, 11, 17), Role.DELIVERYMAN, 30)));
	}
	
	public ArrayList<DeliveryMan> getDeliveryMen() {
		return deliveryMen;
	}

	public void setDeliveryMen(ArrayList<DeliveryMan> deliveryMans) {
		this.deliveryMen = deliveryMans;
	}
	
	public void addDeliveryMan(DeliveryMan deliveryMan) {
		deliveryMen.add(deliveryMan);
	}
	
	public boolean checkUsername(String username) {
		for (DeliveryMan deliveryMan : deliveryMen) {
			if (deliveryMan.getUser().getUsername().equalsIgnoreCase(username))
				return false;
		}
		return true;
	}
	
	public ArrayList<DeliveryMan> getAllDeliveryMen() {
		Gson gson = new Gson();
		
		try {
			Reader reader = Files.newBufferedReader(Paths.get("./static/data/delivery men.json"));
			DeliveryMan[] deliveryMenList = gson.fromJson(reader, DeliveryMan[].class);
			if(deliveryMenList != null) {
			    for (int i = 0; i < deliveryMenList.length; i++) {
			        deliveryMen.add(deliveryMenList[i]);
			    }
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return deliveryMen;
	}
	
	public void saveAllDeliveryMen() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			Writer writer = Files.newBufferedWriter(Paths.get("./static/data/delivery men.json"));
			writer.append(gson.toJson(deliveryMen, DeliveryMan[].class));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
