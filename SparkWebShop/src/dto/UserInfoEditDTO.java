package dto;

public class UserInfoEditDTO {
	private int id;
	private String username;
	private String password;
	private String name;
	private String lastname;
	private String birthDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	
	public UserInfoEditDTO(int id, String username, String password, String name, String lastname, String birthDate) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastname = lastname;
		this.birthDate = birthDate;
	}
	
	
}
