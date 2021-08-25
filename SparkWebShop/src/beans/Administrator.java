package beans;

public class Administrator {

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Administrator(User user) {
		super();
		this.user = user;
	}
}
