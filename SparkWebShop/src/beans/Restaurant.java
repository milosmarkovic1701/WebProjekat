package beans;

import enums.RestaurantStatus;

public class Restaurant {

	private String name;
	private String type;
	private RestaurantStatus status;
	private String logo;
	private int id;
	private double rating;
	private boolean deleted;
	private Location location;
	
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
	public RestaurantStatus getStatus() {
		return status;
	}
	public void setStatus(RestaurantStatus status) {
		this.status = status;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public Location getLocation() {
		return location;
	}
	
	public String getAddress() {
		return location.getStreet() + " " + location.getNumber() + ", " + location.getCity();
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	public Restaurant(String name, String type, RestaurantStatus status, String logo, int id, double rating,
			Location location) {
		super();
		this.name = name;
		this.type = type;
		this.status = status;
		this.logo = logo;
		this.id = id;
		this.rating = rating;
		this.location = location;
		this.deleted = false;
	}
	
	
}
