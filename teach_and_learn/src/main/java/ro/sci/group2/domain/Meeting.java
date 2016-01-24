package ro.sci.group2.domain;

import java.util.Collection;

import org.joda.time.ReadableInterval;

public class Meeting extends AbstractModel {

	private Teacher teacher;
	private String city;
	private String location;
	private ReadableInterval meetingInterval;
	private Course course; //or maybe courseId
	private String observation;
	private Collection<Student> attendees;
	
	public Meeting(long id){
		setId(id);
	}
	public Meeting(){
		this(0);
	}

	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
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

	public ReadableInterval getMeetingInterval() {
		return meetingInterval;
	}

	public void setMeetingInterval(ReadableInterval meetingInterval) {
		this.meetingInterval = meetingInterval;
	}

	public Course getSubject() {
		return course;
	}

	public void setSubject(Course subject) {
		this.course = subject;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}
	public Collection<Student> getAttendees() {
		return attendees;
	}
	public void setAttendees(Collection<Student> attendees) {
		this.attendees = attendees;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((meetingInterval == null) ? 0 : meetingInterval.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Meeting other = (Meeting) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.toLowerCase().equals(other.city.toLowerCase()))
			return false;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.toLowerCase().equals(other.location.toLowerCase()))
			return false;
		if (meetingInterval == null) {
			if (other.meetingInterval != null)
				return false;
		} else if (!meetingInterval.equals(other.meetingInterval))
			return false;
		return true;
	}
}
