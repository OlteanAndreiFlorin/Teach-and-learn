/**
 * 
 */
package ro.sci.group2.service;

import java.util.Collection;
import java.util.LinkedList;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.sci.group2.dao.MeetingDAO;
import ro.sci.group2.domain.Course;
import ro.sci.group2.domain.Meeting;
import ro.sci.group2.domain.User;

@Service
public class MeetingService {

	@Autowired
	private MeetingDAO dao;

	public Meeting save(Meeting meeting) {
		return dao.update(meeting);
	}

	public Collection<Meeting> listAll() {
		return dao.getAll();
	}

	public boolean delete(long id) {
		Meeting meeting = dao.findById(id);
		if (meeting == null) {
			return false;
		} else {
			return dao.delete(meeting);
		}
	}

	public Meeting findById(long id) {
		Meeting meeting = dao.findById(id);
		return meeting;
	}

	public Collection<Meeting> searchByTeacher(Long id) {
		Collection<Meeting> result = dao.searchByTeacher(id);
		if (result == null) {
			result = new LinkedList<>();
		}
		return result;
	}

	public Collection<Meeting> searchByCity(String city) {
		return dao.searchByCity(city);
	}

	public Collection<Meeting> searchByDate(DateTime date) {
		return dao.searchByDate(date);
	}

	public Collection<Meeting> searchByCourse(Course course) {
		return dao.searchByCourse(course);
	}

	public Collection<Meeting> searchByAttendee(Long id) {
		return dao.searchByAttendee(id);
	}

	public void addAttendee(Long id, User user) {
		Meeting meeting = dao.findById(id);
		Collection<User> attendees = meeting.getAttendees();
		if (!attendees.contains(user)) {
			attendees.add(user);
			meeting.setAttendees(attendees);
			dao.update(meeting);
		}
	}

	public boolean removeAttendee(Long id, User user) {
		Meeting meeting = dao.findById(id);
		Collection<User> attendees = meeting.getAttendees();
		if (attendees.contains(user)) {
			attendees.remove(user);
			meeting.setAttendees(attendees);
			dao.update(meeting);
			return true;
		}
		return false;
	}

}
