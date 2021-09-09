package dto;

public class OrderQueryDTO {

	private String restaurantName;
	private String priceDown;
	private String priceUp;
	private String dateDown;
	private String dateUp;
	private String filterType;
	private String filterStatus;
	private String sort;
	private int customerId; 
	
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getPriceDown() {
		return priceDown;
	}
	public void setPriceDown(String priceDown) {
		this.priceDown = priceDown;
	}
	public String getPriceUp() {
		return priceUp;
	}
	public void setPriceUp(String priceUp) {
		this.priceUp = priceUp;
	}
	public String getDateDown() {
		return dateDown;
	}
	public void setDateDown(String dateDown) {
		this.dateDown = dateDown;
	}
	public String getDateUp() {
		return dateUp;
	}
	public void setDateUp(String dateUp) {
		this.dateUp = dateUp;
	}
	public String getFilterType() {
		return filterType;
	}
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
	public String getFilterStatus() {
		return filterStatus;
	}
	public void setFilterStatus(String filterStatus) {
		this.filterStatus = filterStatus;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public OrderQueryDTO(String restaurantName, String priceDown, String priceUp, String dateDown, String dateUp,
			String filterType, String filterStatus, String sort, int customerId) {
		super();
		this.restaurantName = restaurantName;
		this.priceDown = priceDown;
		this.priceUp = priceUp;
		this.dateDown = dateDown;
		this.dateUp = dateUp;
		this.filterType = filterType;
		this.filterStatus = filterStatus;
		this.sort = sort;
		this.customerId = customerId;
	}
	
	

}
