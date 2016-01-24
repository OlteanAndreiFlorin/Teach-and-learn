/**
 * 
 */
package ro.sci.group2.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Razvan Radu
 *
 */
public class Meeting {

	private HashSet<User> tenants = new HashSet<>();
	private Calendar setDate;
	private Calendar recurrency;
	private Subject meetingSubject;
	private boolean isActive;
	
	
	public Meeting(Calendar setDate , Calendar recurrency, Subject meetingSubject){
		this.setDate = setDate;
		this.recurrency = recurrency;
		this.meetingSubject = meetingSubject;
		this.isActive=true;
	}


	/**
	 * @return the tenants
	 */
	public Set<? extends User> getListOfTenants() {
		return tenants;
	}


	/**
	 * @param tenants the tenant to add
	 */
	public void addUser(User student) {
		this.tenants.add(student);
	}
	
	/**
	 * @param tenants the tenant to be searched for
	 */
	public boolean findUser(User user) {

		if (tenants.contains(user)) {
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean removeUser(User user){
		
		if (this.findUser(user)) {
			tenants.remove(user);
			return true;
		}
		else
		{
			return false;
		}
		
	}

	/**
	 * @return the setDate
	 */
	public Calendar getSetDate() {
		return setDate;
	}


	/**
	 * @param setDate the setDate to set
	 */
	public void setSetDate(Calendar setDate) {
		this.setDate = setDate;
	}


	/**
	 * @return the recurrency
	 */
	public Calendar getRecurrency() {
		return recurrency;
	}


	/**
	 * @param recurrency the recurrency to set
	 */
	public void setRecurrency(Calendar recurrency) {
		this.recurrency = recurrency;
	}


	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}


	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + ((meetingSubject == null) ? 0 : meetingSubject.hashCode());
		result = prime * result + ((recurrency == null) ? 0 : recurrency.hashCode());
		result = prime * result + ((setDate == null) ? 0 : setDate.hashCode());
		result = prime * result + ((tenants == null) ? 0 : tenants.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Meeting other = (Meeting) obj;
		if (isActive != other.isActive)
			return false;
		if (meetingSubject == null) {
			if (other.meetingSubject != null)
				return false;
		} else if (!meetingSubject.equals(other.meetingSubject))
			return false;
		if (recurrency == null) {
			if (other.recurrency != null)
				return false;
		} else if (!recurrency.equals(other.recurrency))
			return false;
		if (setDate == null) {
			if (other.setDate != null)
				return false;
		} else if (!setDate.equals(other.setDate))
			return false;
		if (tenants == null) {
			if (other.tenants != null)
				return false;
		} else if (!tenants.equals(other.tenants))
			return false;
		return true;
	}
	
	
}
