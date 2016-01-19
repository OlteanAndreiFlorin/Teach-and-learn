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

	private Set<? extends User> tenants = new HashSet<>();
	private Calendar setDate;
	private Calendar recurrency;
	private Subject meetingSubject;
	
	
	public Meeting(Calendar setDate , Calendar recurrency, Subject meetingSubject){
		this.setDate = setDate;
		this.recurrency = recurrency;
		this.meetingSubject = meetingSubject;
	}
}
