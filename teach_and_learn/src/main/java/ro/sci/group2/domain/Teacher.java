/**
 * 
 */
package ro.sci.group2.domain;

import java.util.Collection;

/**
 * @author Razvan Radu
 *
 */
public class Teacher extends User {
	private String firstName;
	private String lastName;
	private String address;
	private Collection<Course> myCompetences;
	
	
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	public Collection<Course> getMyCompetences() {
		return myCompetences;
	}
	public void setMyCompetences(Collection<Course> myCompetences) {
		this.myCompetences = myCompetences;
	}
	
	
	
}
