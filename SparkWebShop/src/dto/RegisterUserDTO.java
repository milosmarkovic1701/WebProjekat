package dto;

public class RegisterUserDTO {

	private String usernameRegister;
	private String passwordRegister;
	private String name;
	private String lastname;
	private String birthDate;
	private String address;
	
	public String getUsernameRegister() {
		return usernameRegister;
	}
	public void setUsernameRegister(String usernameRegister) {
		this.usernameRegister = usernameRegister;
	}
	public String getPasswordRegister() {
		return passwordRegister;
	}
	public void setPasswordRegister(String passwordRegister) {
		this.passwordRegister = passwordRegister;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public RegisterUserDTO(String usernameRegister, String passwordRegister, String name, String lastname,
			String birthDate, String address) {
		super();
		this.usernameRegister = usernameRegister;
		this.passwordRegister = passwordRegister;
		this.name = name;
		this.lastname = lastname;
		this.birthDate = birthDate;
		this.address = address;
	}
	
	
}
