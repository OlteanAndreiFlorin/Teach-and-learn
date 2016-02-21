package ro.sci.group2.domain;

import java.util.Collection;
import java.util.LinkedList;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * The meeting class models a meeting that a teacher can initiate inside the
 * meeting the following information are saved: The teacher who initiated the
 * meeting, the city and location where it takes place,the time interval of the
 * meeting,observations that the teacher might add for the meeting the course
 * that will be taught and a list with the attendees;
 * 
 * @author Andrei,Razvan
 *
 */
public class Meeting extends AbstractModel {

	private User teacher;

	@NotNull
	private String city;

	@NotNull
	private String location;

	@DateTimeFormat(pattern = "YYYY-MM-DD HH:mm")
	private DateTime meetingDate;

	@DateTimeFormat(pattern = "HH:mm")
	private DateTime duration;

	@NotNull
	private String observation;

	@NotNull
	private Collection<User> attendees = new LinkedList<>();

	@NotNull
	private Course course;

	@Min(0)
	@Max(100)
	private int maxAttendance;

	@Min(0)
	@Max(100)
	private int currentAttendance;

	public Meeting(long id) {
		setId(id);
	}

	public Meeting() {
		this(0);
	}

	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public DateTime getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(DateTime meetingDate) {
		this.meetingDate = meetingDate;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Collection<User> getAttendees() {
		return attendees;
	}

	public void setAttendees(Collection<User> attendees) {
		this.attendees = attendees;
		this.currentAttendance = attendees.size();
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * @return the recurrency
	 */
	public DateTime getDuration() {
		return duration;
	}

	/**
	 * @param recurrency
	 *            the recurrency to set
	 */
	public void setDuration(DateTime duration) {
		this.duration = duration;
	}

	/**
	 * @return the maxAttendance
	 */
	public int getMaxAttendance() {
		return maxAttendance;
	}

	/**
	 * @param maxAttendance
	 *            the maxAttendance to set
	 */
	public void setMaxAttendance(int maxAttendance) {
		this.maxAttendance = maxAttendance;
	}

	public int getCurrentAttendance() {
		return attendees.size();
	}

	public boolean isFull() {
		return (this.getCurrentAttendance() >= this.getMaxAttendance());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + ((meetingDate == null) ? 0 : meetingDate.hashCode());
		result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Meeting))
			return false;
		Meeting other = (Meeting) obj;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course)){
			System.out.println("ding");
			return false;}
		if (meetingDate == null) {
			if (other.meetingDate != null)
				return false;
		} else if (!meetingDate.equals(other.meetingDate))
			return false;
		if (teacher == null) {
			if (other.teacher != null)
				return false;
		} else if (!teacher.equals(other.teacher))
			return false;
		return true;
	}

}
