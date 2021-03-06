package beans;

import java.time.LocalDate;

import enums.Role;

public class User {
	private String username;
	private String password;
	private String name;
	private String lastName;
	private String fullName;
	private LocalDate birthDate;
	private String dateInfo;
	private Role role;
	private boolean deleted;
	private int id;
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getDateInfo() {
		return dateInfo;
	}
	public void setDateInfo(LocalDate birthdate) {
		String year = String.valueOf(birthdate.getYear());
		String month;
		if (birthdate.getMonthValue() < 10)
			month = "0" + String.valueOf(birthdate.getMonthValue());
		else
			month = String.valueOf(birthdate.getMonthValue());
		String day;
		if (birthdate.getDayOfMonth() < 10)
			day = "0" + String.valueOf(birthdate.getDayOfMonth());
		else
			day = String.valueOf(birthdate.getDayOfMonth());
		this.dateInfo = year + "-" + month + "-" + day;
	}
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
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public User(String username, String password, String name, String lastName, LocalDate birthDate, Role role, int id) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.fullName = name + " " + lastName;
		this.birthDate = birthDate;
		String year = String.valueOf(birthDate.getYear());
		String month;
		if (birthDate.getMonthValue() < 10)
			month = "0" + String.valueOf(birthDate.getMonthValue());
		else
			month = String.valueOf(birthDate.getMonthValue());
		String day;
		if (birthDate.getDayOfMonth() < 10)
			day = "0" + String.valueOf(birthDate.getDayOfMonth());
		else
			day = String.valueOf(birthDate.getDayOfMonth());
		this.dateInfo = year + "-" + month + "-" + day;
		this.role = role;
		this.deleted = false;
		this.id = id;
	}
	
}
