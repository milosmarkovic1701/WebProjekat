package dto;

public class RestaurantQueryDTO {
//searchQuery : {name: "", type: "", location: "", rating: 0, filterType: "", filterStatus: "", sort: ""},
	private String name;
	private String type;
	private String location;
	private String rating;
	private String filterType;
	private String filterStatus;
	private String sort;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
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
	
	public RestaurantQueryDTO(String name, String type, String location, String rating, String filterType,
			String filterStatus, String sort) {
		super();
		this.name = name;
		this.type = type;
		this.location = location;
		this.rating = rating;
		this.filterType = filterType;
		this.filterStatus = filterStatus;
		this.sort = sort;
	}	
}
