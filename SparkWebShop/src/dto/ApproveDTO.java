package dto;

public class ApproveDTO {
	private int orderId;
	private int restaurantId;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public ApproveDTO(int orderId, int restaurantId) {
		super();
		this.orderId = orderId;
		this.restaurantId = restaurantId;
	}
	
}
