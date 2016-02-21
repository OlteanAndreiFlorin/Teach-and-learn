package ro.sci.group2.dao;

import java.util.Collection;

import org.joda.time.DateTime;

import ro.sci.group2.domain.Course;
import ro.sci.group2.domain.Meeting;

public interface MeetingDAO extends BaseDAO<Meeting> {

	Collection<Meeting> searchByTeacher(Long id);
	Collection<Meeting> searchByCity(String query);

	/**
	 * 
	 * @param interval
	 * @see <a href=
	 *      "http://www.joda.org/joda-time/apidocs/org/joda/time/Interval.html">
	 *      Joda-Interval</a> the interval in which to find meetings
	 * @return A collection with all the meetings that happen in the interval
	 */
	Collection<Meeting> searchByDate(DateTime date);
	Collection<Meeting> searchByCourse(Course course);
	Collection<Meeting> searchByAttendee(Long id);
}
