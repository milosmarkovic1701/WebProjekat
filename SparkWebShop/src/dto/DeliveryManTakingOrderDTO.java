package dto;

import java.time.LocalDateTime;

public class DeliveryManTakingOrderDTO {

	private String deliveryManName;
	private String deliveryManLastName;
	private String orderInfo;
	private String orderDateTime;
	private double orderPrice;
	public String getDeliveryManName() {
		return deliveryManName;
	}
	public void setDeliveryManName(String deliveryManName) {
		this.deliveryManName = deliveryManName;
	}
	public String getDeliveryManLastName() {
		return deliveryManLastName;
	}
	public void setDeliveryManLastName(String deliveryManLastName) {
		this.deliveryManLastName = deliveryManLastName;
	}
	public String getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	public String getOrderDateTime() {
		return orderDateTime;
	}
	public void setOrderDateTime(String orderDateTime) {
		this.orderDateTime = orderDateTime;
	}
	public double getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}
	public DeliveryManTakingOrderDTO(String deliveryManName, String deliveryManLastName, String orderInfo,
			String orderDateTime, double orderPrice) {
		super();
		this.deliveryManName = deliveryManName;
		this.deliveryManLastName = deliveryManLastName;
		this.orderInfo = orderInfo;
		this.orderDateTime = orderDateTime;
		this.orderPrice = orderPrice;
	}
}
