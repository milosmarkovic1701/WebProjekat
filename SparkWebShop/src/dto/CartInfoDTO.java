package dto;

public class CartInfoDTO {

	private int customerId;
	private int foodItemId;
	private int amount;
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getFoodItemId() {
		return foodItemId;
	}
	public void setFoodItemId(int foodItemId) {
		this.foodItemId = foodItemId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public CartInfoDTO(int customerId, int foodItemId, int amount) {
		super();
		this.customerId = customerId;
		this.foodItemId = foodItemId;
		this.amount = amount;
	}
	
	
}
