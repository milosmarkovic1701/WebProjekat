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
import beans.Order;
import beans.User;
import dto.AdminCommentDTO;
import dto.ApproveDTO;
import dto.DeliveryManTakingOrderDTO;
import dto.LoginUserDTO;
import enums.Role;
import enums.OrderStatus;

public class DeliveryManService {
	
	ArrayList<DeliveryMan> deliveryMen = new ArrayList<DeliveryMan>();
	OrderService orderService = new OrderService();
	public DeliveryManService() {
		deliveryMen = getAllDeliveryMen();
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
		deliveryMen.clear();
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
			writer.append(gson.toJson(deliveryMen));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public DeliveryMan login(LoginUserDTO user) {
		deliveryMen = getAllDeliveryMen();
		for (DeliveryMan deliveryMen : deliveryMen) {
			if (user.getUsernameLogin().equals(deliveryMen.getUser().getUsername()) && user.getPasswordLogin().equals(deliveryMen.getUser().getPassword()))
				return deliveryMen;
		}
		return null;
	}
	public ArrayList<DeliveryManTakingOrderDTO> getPotentialDeliveyMen(int id){
		ArrayList<Order> orders = orderService.getAllOrders();
		deliveryMen = this.getAllDeliveryMen();
		ArrayList<Order> restaurantOrders = new ArrayList<Order>();
		for(Order o:orders) {
			if(o.getRestaurantId()==id && o.getDeliveryId()!=0 && o.getStatus()==OrderStatus.READY_TO_DELIVER) {
				restaurantOrders.add(o);
			}
		}
		ArrayList<DeliveryManTakingOrderDTO> deliveryMenDTO = new ArrayList<DeliveryManTakingOrderDTO>();
		
		for(Order o:restaurantOrders) {
			DeliveryMan deliveryMan = null;
			for(DeliveryMan dm:deliveryMen) {
				if(dm.getUser().getId()==o.getDeliveryId()) {
					deliveryMan = dm;
				}
			}
			DeliveryManTakingOrderDTO deliveryManDTO = new DeliveryManTakingOrderDTO(deliveryMan.getUser().getName(),deliveryMan.getUser().getLastName(),o.getOrderInfo(),o.getDateInfo(),o.getPrice(),o.getId(),o.getRestaurantId());
			deliveryMenDTO.add(deliveryManDTO);
		}
		return deliveryMenDTO;
	}
	public ArrayList<DeliveryManTakingOrderDTO> acceptDeliveryMan(ApproveDTO ap){
		ArrayList<Order> orders = orderService.getAllOrders();
		for(Order o: orders) {
			if(o.getId() == ap.getOrderId()) {
				o.setStatus(OrderStatus.IN_TRANSPORT);
			}
			orderService.saveAllOrders();
			
		}
		return this.getPotentialDeliveyMen(ap.getRestaurantId());
		 
	}
	public ArrayList<DeliveryManTakingOrderDTO> declineDeliveryMan(ApproveDTO ap){
		ArrayList<Order> orders = orderService.getAllOrders();
		for(Order o: orders) {
			if(o.getId() == ap.getOrderId()) {
				o.setDeliveryId(0);
			}
			orderService.saveAllOrders();
			
		}
		return this.getPotentialDeliveyMen(ap.getRestaurantId());
		 
	}
	
	
}
