package ro.sci.group2.domain;

import java.util.Collection;

import org.joda.time.ReadableInterval;

public class Meeting extends AbstractModel {

	private Teacher teacher;
	private String city;
	private String location;
	private ReadableInterval meetingInterval;
	private String observation;
	private Collection<Student> attendees;

	public Meeting(long id) {
		setId(id);
	}

	public Meeting() {
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

}
