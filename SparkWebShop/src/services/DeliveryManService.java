package services;

import java.time.LocalDate;
import java.util.ArrayList;

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
}
