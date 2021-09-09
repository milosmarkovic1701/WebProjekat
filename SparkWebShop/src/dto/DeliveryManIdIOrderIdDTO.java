package dto;

public class DeliveryManIdIOrderIdDTO {
	private int orderId;
	private int deliveryId;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(int deliveryId) {
		this.deliveryId = deliveryId;
	}
	public DeliveryManIdIOrderIdDTO(int orderId, int deliveryId) {
		super();
		this.orderId = orderId;
		this.deliveryId = deliveryId;
	}
	
}
