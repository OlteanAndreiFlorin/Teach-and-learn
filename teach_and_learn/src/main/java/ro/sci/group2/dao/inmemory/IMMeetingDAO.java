/**
 * 
 */
package ro.sci.group2.dao.inmemory;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import ro.sci.group2.dao.MeetingDAO;
import ro.sci.group2.domain.Course;
import ro.sci.group2.domain.Meeting;
import ro.sci.group2.domain.User;

/**
 * @author Razvan, Andrei, Hopy
 *
 */
public class IMMeetingDAO extends IMBaseDAO<Meeting> implements MeetingDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see ro.sci.group2.dao.BaseDAO#getAll()
	 */
	
	public Collection<Meeting> listMeetings() {
		Collection<Meeting> all = new LinkedList<Meeting>(getAll());
		return all;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ro.sci.group2.dao.MeetingDAO#searchByTeacher(java.lang.String)
	 */
	@Override
	public Collection<Meeting> searchByTeacher(Long id) {
		if (id == 0) {
			return getAll();
		}
		Collection<Meeting> all = new LinkedList<>(getAll());
		for (Iterator<Meeting> it = all.iterator(); it.hasNext();) {
			Meeting meeting = it.next();
			Long ss = meeting.getTeacher().getId();
			if (!ss.equals(id)) {
				it.remove();
			}
		}
		return all;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ro.sci.group2.dao.MeetingDAO#searchByCity(java.lang.String)
	 */
	@Override
	public Collection<Meeting> searchByCity(String query) {
		if (StringUtils.isEmpty(query)) {
			return getAll();
		}
		Collection<Meeting> all = new LinkedList<>(getAll());
		for (Iterator<Meeting> it = all.iterator(); it.hasNext();) {
			Meeting meeting = it.next();
			String ss = meeting.getCity();
			if (!ss.toLowerCase().equals(query.toLowerCase())) {
				it.remove();
			}
		}
		return all;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ro.sci.group2.dao.MeetingDAO#searchByDate(org.joda.time.Interval)
	 */
	
	public Collection<Meeting> searchByDate(DateTime date) {
		if (null == date) {
			return getAll();
		}
		Collection<Meeting> all = new LinkedList<>(getAll());
		for (Iterator<Meeting> it = all.iterator(); it.hasNext();) {
			Meeting meeting = it.next();
			if ((!date.equals(meeting.getMeetingDate()))) {
				it.remove();
			}
		}
		return all;
	}

	@Override
	public Collection<Meeting> searchByCourse(Course course) {
		if (null == course) {
			return getAll();
		}
		Collection<Meeting> all = new LinkedList<>(getAll());
		for (Iterator<Meeting> it = all.iterator(); it.hasNext();) {
			Meeting meeting = it.next();
			if ((!meeting.getCourse().equals(course))) {
				it.remove();
			}
		}
		return all;
	}

	@Override
	public Collection<Meeting> searchByAttendee(Long id) {
		boolean isOk;

		if (id == 0) {
			return getAll();
		}
		Collection<Meeting> all = new LinkedList<>(getAll());
		for (Iterator<Meeting> it = all.iterator(); it.hasNext();) {
			Meeting meeting = it.next();
			Collection<User> allUsers = new LinkedList<>(meeting.getAttendees());
			isOk = false;
			for (Iterator<User> iter = allUsers.iterator(); iter.hasNext();) {
				User test = iter.next();
				if (test.getId() == id) {
					isOk = true;
				}
			}
			if (!isOk) {
				it.remove();
			}
		}
		return all;
	}

}
