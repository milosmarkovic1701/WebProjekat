package dto;

import java.util.ArrayList;

public class OrderDeliveryManDTO {
	private ArrayList<RestaurantOrderDTO> orders;
	private ArrayList<DeliveryManTakingOrderDTO> deliveryMen;
	public ArrayList<RestaurantOrderDTO> getOrders() {
		return orders;
	}
	public void setOrders(ArrayList<RestaurantOrderDTO> orders) {
		this.orders = orders;
	}
	public ArrayList<DeliveryManTakingOrderDTO> getDeliveryMen() {
		return deliveryMen;
	}
	public void setDeliveryMen(ArrayList<DeliveryManTakingOrderDTO> deliveryMen) {
		this.deliveryMen = deliveryMen;
	}
	public OrderDeliveryManDTO(ArrayList<RestaurantOrderDTO> orders, ArrayList<DeliveryManTakingOrderDTO> deliveryMen) {
		super();
		this.orders = orders;
		this.deliveryMen = deliveryMen;
	}
	
	
	
}
