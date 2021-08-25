package beans;

public class Location {

	private String street;
	private String number;
	private String city;
	private String postalCode;
	private double length;
	private double width;
	
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
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	
	public Location(String street, String number, String city, String postalCode, double length, double width) {
		super();
		this.street = street;
		this.number = number;
		this.city = city;
		this.postalCode = postalCode;
		this.length = length;
		this.width = width;
	}
	
	
}
