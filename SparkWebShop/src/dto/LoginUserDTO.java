package dto;

public class LoginUserDTO {

	private String usernameLogin;
	private String passwordLogin;
	
	public LoginUserDTO(String usernameLogin, String passwordLogin) {
		super();
		this.usernameLogin = usernameLogin;
		this.passwordLogin = passwordLogin;
	}
	
	public String getUsernameLogin() {
		return usernameLogin;
	}
	public void setUsernameLogin(String usernameLogin) {
		this.usernameLogin = usernameLogin;
	}
	public String getPasswordLogin() {
		return passwordLogin;
	}
	public void setPasswordLogin(String passwordLogin) {
		this.passwordLogin = passwordLogin;
	}
}
