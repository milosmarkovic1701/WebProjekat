package dto;

public class UsersQueryDTO {

	private String username;
	private String name;
	private String lastname;
	private String filterRole;
	private String filterType;
	private String sort;
	
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
	public String getFilterRole() {
		return filterRole;
	}
	public void setFilterRole(String filterRole) {
		this.filterRole = filterRole;
	}
	public String getFilterType() {
		return filterType;
	}
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public UsersQueryDTO(String username, String name, String lastname, String filterRole, String filterType,
			String sort) {
		super();
		this.username = username;
		this.name = name;
		this.lastname = lastname;
		this.filterRole = filterRole;
		this.filterType = filterType;
		this.sort = sort;
	}
}
