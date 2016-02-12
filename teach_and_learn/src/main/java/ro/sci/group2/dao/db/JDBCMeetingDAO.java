package ro.sci.group2.dao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.sci.group2.dao.MeetingDAO;
import ro.sci.group2.domain.Course;
import ro.sci.group2.domain.Meeting;
import ro.sci.group2.service.DatabaseManager;

/**
 * JDBC implementation for {@link MeetingDAO}.
 * 
 * @author Oltean Andrei
 *
 */
public class JDBCMeetingDAO implements MeetingDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(JDBCMeetingDAO.class);

	private String host;
	private String port;
	private String dbName;
	private String userName;
	private String pass;

	public JDBCMeetingDAO(String host, String port, String dbName, String userName, String pass) {
		super();
		this.host = host;
		this.port = port;
		this.dbName = dbName;
		this.userName = userName;
		this.pass = pass;
	}

	protected Connection newConnection() {
		try {
			Class.forName("org.postgresql.Driver").newInstance();

			String url = new StringBuilder().append("jdbc:").append("postgresql").append("://").append(host).append(":")
					.append(port).append("/").append(dbName).append("?user=").append(userName).append("&password=")
					.append(pass).toString();
			Connection result = DriverManager.getConnection(url);
			return result;
		} catch (Exception ex) {
			throw new RuntimeException("Error getting DB connectio", ex);
		}
	}

	private Meeting exctractMeeting(ResultSet rs) throws SQLException {
		DatabaseManager dbManager = new DatabaseManager();
		Meeting meeting = new Meeting();
		meeting.setId(rs.getLong("id"));
		meeting.setCity(rs.getString("city"));
		meeting.setLocation(rs.getString("location"));
		meeting.setMaxAttendance(rs.getInt("max_atendees"));
		meeting.setObservation(rs.getString("observation"));
		meeting.setDuration(new DateTime(rs.getDate("duration")));
		meeting.setMeetingInterval(new DateTime(rs.getDate("meeting_date")));
		meeting.setCourse(dbManager.findCourse(rs.getLong("course_id")));
		meeting.setTeacher(dbManager.findTeacher(rs.getLong("teahcer_id")));
		meeting.setAttendees(dbManager.convertStringToUsers(rs.getString("atendees_id")));
		return meeting;
	}

	@Override
	public Collection<Meeting> getAll() {
		Connection connection = newConnection();

		Collection<Meeting> result = new LinkedList<>();
		try (ResultSet rs = connection.createStatement().executeQuery("select * from meeting")) {
			while (rs.next()) {
				result.add(exctractMeeting(rs));
			}
			connection.commit();
		} catch (SQLException e) {
			throw new RuntimeException("Error getting meetings from Db!", e);
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
			}
		}
		return result;
	}

	@Override
	public Meeting findById(Long id) {
		Connection connection = newConnection();
		List<Meeting> result = new LinkedList<>();
		try (ResultSet rs = connection.createStatement().executeQuery("select * from meeting where id = " + id)) {
			while (rs.next()) {
				result.add(exctractMeeting(rs));
			}
			connection.commit();
		} catch (SQLException e) {
			throw new RuntimeException("Error getting meeting from DB!", e);
		}finally{
			try{
				connection.close();
			}catch(Exception e){
				
			}
		}
		if(result.size()>1){
			throw new IllegalStateException("Multiple Meetings found for id" + id);
		}

		return result.isEmpty() ? null : result.get(0);

	}

	@Override
	public Meeting update(Meeting model) {
		Connection connection = newConnection();
		try{
			PreparedStatement ps = null;
			if(model.getId()>0){
				ps =connection.prepareStatement("update meeting set ")
			}
		}
		return null;
	}

	@Override
	public boolean delete(Meeting model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Meeting> searchByTeacher(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Meeting> searchByCity(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Meeting> searchByDate(String interval) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Meeting> searchByCourse(Course course) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Meeting> searchByAttendee(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
