package dto;

public class RestaurantDTO {

	private String name;
	private String type;
	private String logo;
	private String street;
	private String number;
	private String city;
	private String latitude;
	private String longitude;
	private String postalCode;
	private String managerId;
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
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	
	public RestaurantDTO(String name, String type, String logo, String street, String number, String city,
			String latitude, String longitude, String postalCode, String managerId) {
		super();
		this.name = name;
		this.type = type;
		this.logo = logo;
		this.street = street;
		this.number = number;
		this.city = city;
		this.latitude = latitude;
		this.longitude = longitude;
		this.postalCode = postalCode;
		this.managerId = managerId;
	}
	
	
	
}
