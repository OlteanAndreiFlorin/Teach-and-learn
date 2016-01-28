/**
 * 
 */
package ro.sci.group2.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Razvan Radu
 *
 */
public class User extends AbstractModel {
	private String userName;
	private int userPassCode;
	private Set<Role> roles=new HashSet<>();
	private boolean isLogged;
	
	public User(long id) {
		setId(id);
	}

	public User() {
		this(0);
	}
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setLogged(boolean logged){
		this.isLogged = logged;
	}

	/**
	 * @return true if the password matches the one in the system and false if not
	 */
	protected boolean checkUserPass(UserPassword pass) {
		if (this.userPassCode == pass.hashCode()) {
			this.setLogged(true);
			return true;
		}
		else {
			this.setLogged(false);
			return false;
		}
			
	}

	/**
	 * @param userPass the {@link UserPassword User password} to set
	 */
	protected void setUserPass(UserPassword pass) {
		if (this.isLogged) {
			this.userPassCode = pass.hashCode();
		}
		else {
			throw new IllegalArgumentException("Not logged in, cannot change existing password");
		}
		
	}

	public void addRole(Role role){
		this.roles.add(role);
	}
	
	public void removeRole(Role role){
		if (this.roles.contains(role)) {
			this.roles.remove(role);
		}
		else {
			throw new IllegalArgumentException("Cannot remove non-existing role");
		}
	}
	
	
}
