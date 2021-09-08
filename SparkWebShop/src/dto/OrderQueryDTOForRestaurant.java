package dto;

public class OrderQueryDTOForRestaurant {
	
	private int restaurantId;
	private String priceDown;
	private String priceUp;
	private String dateDown;
	private String dateUp;
	private String filterStatus;
	private String sort;
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
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
	public OrderQueryDTOForRestaurant(int restaurantId, String priceDown, String priceUp, String dateDown,
			String dateUp, String filterStatus, String sort) {
		super();
		this.restaurantId = restaurantId;
		this.priceDown = priceDown;
		this.priceUp = priceUp;
		this.dateDown = dateDown;
		this.dateUp = dateUp;
		this.filterStatus = filterStatus;
		this.sort = sort;
	}
	
	
	
	
}
