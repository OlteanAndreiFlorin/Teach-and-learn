package ro.sci.group2.domain;

import java.util.Collection;
import java.util.LinkedList;

public class User extends AbstractModel {

	private String password;
	private final String username;
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private String phone;
	private Gender gender;
	private Collection<Role> roles = new LinkedList<>();
	private Collection<Course> courses = new LinkedList<>();

	public User(long id, String username) {
		setId(id);
		this.username = username;
	}

	public User() {
		this(0, "mock");
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public Collection<Course> getCourses() {
		return this.courses;
	}

	public void setCourses(Collection<Course> courses) {
		this.courses = courses;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public int hashCode() {
		return username.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			return username.equals(((User) obj).username);
		}
		return false;
	}
}
