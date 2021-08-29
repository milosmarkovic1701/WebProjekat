package dto;

public class UserDTO {

	private String id;
	private String username;
	private String name;
	private String lastname;
	private String birthDate;
	private String role;
	private String points;
	private String userType;
	private String cancels;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getCancels() {
		return cancels;
	}
	public void setCancels(String cancels) {
		this.cancels = cancels;
	}
	
	public UserDTO(String id, String username, String name, String lastname, String birthDate, String role,
			String points, String userType, String cancels) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.lastname = lastname;
		this.birthDate = birthDate;
		this.role = role;
		this.points = points;
		this.userType = userType;
		this.cancels = cancels;
	}
	
	
}
