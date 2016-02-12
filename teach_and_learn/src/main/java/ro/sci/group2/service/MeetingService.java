/**
 * 
 */
package ro.sci.group2.service;

import java.util.Collection;


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
		return dao.searchByTeacher(id);
	}

	public Collection<Meeting> searchByCity(String city) {
		return dao.searchByCity(city);
	}

	public Collection<Meeting> searchByDate(String interval) {
		return dao.searchByDate(interval);
	}

	public Collection<Meeting> searchByCourse(Course course) {
		return dao.searchByCourse(course);
	}

	public Collection<Meeting> searchByAttendee(Long id) {
		return dao.searchByAttendee(id);
	}

	public void addAttendee(User user) {

		
	}

	public boolean removeAttendee(User user) {

		return true;
	}

}