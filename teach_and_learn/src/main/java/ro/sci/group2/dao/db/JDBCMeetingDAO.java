package ro.sci.group2.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
			result.setAutoCommit(false);
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
		meeting.setMaxAttendance(rs.getInt("max_attendees"));
		meeting.setObservation(rs.getString("observation"));
		meeting.setDuration(new DateTime(rs.getDate("duration")));
		meeting.setMeetingDate(new DateTime(rs.getDate("meeting_date")));
		meeting.setCourse(dbManager.findCourse(rs.getLong("course_id")));
		meeting.setTeacher(dbManager.findTeacher(rs.getLong("teahcer_id")));
		meeting.setAttendees(dbManager.convertStringToUsers(rs.getString("attendees_id")));
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
		} finally {
			try {
				connection.close();
			} catch (Exception e) {

			}
		}
		if (result.size() > 1) {
			throw new IllegalStateException("Multiple Meetings found for id" + id);
		}

		return result.isEmpty() ? null : result.get(0);

	}

	@Override
	public Meeting update(Meeting model) {
		Connection connection = newConnection();
		try {
			PreparedStatement ps = null;
			if (model.getId() > 0) {
				ps = connection
						.prepareStatement("update meeting set city=?, location=?, max_attendees=?, observation=?,"
								+ " duration=?, meeting_date=?, course_id=?, teacher_id=?, attendees_id=?"
								+ " where id = ? returning id");
			} else {
				ps = connection.prepareStatement(
						"insert into meeting (city, location, max_attendees, observation, duration, meeting_date, "
								+ "course_id, teacher_id, attendees_id) values(?,?,?,?,?,?,?,?,?)" + " returning id");
			}
			ps.setString(1, model.getCity());
			ps.setString(2, model.getLocation());
			ps.setInt(3, model.getMaxAttendance());
			ps.setString(4, model.getObservation());
			ps.setDate(5, (Date) model.getDuration().toDate());
			ps.setDate(6, (Date) model.getMeetingDate().toDate());
			ps.setLong(7, model.getCourse().getId());
			ps.setLong(8, model.getTeacher().getId());
			DatabaseManager mg = new DatabaseManager();
			ps.setString(9, mg.convertUserToString(model.getAttendees()));

			if (model.getId() > 0) {
				ps.setLong(10, model.getId());
			}

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				model.setId(rs.getLong(1));
			}
			rs.close();
			connection.commit();
		} catch (SQLException e) {
			throw new RuntimeException("Error getting meeting from db!", e);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}
		return model;
	}

	@Override
	public boolean delete(Meeting model) {
		boolean result = false;
		Connection connection = newConnection();
		try {
			Statement statement = connection.createStatement();
			statement.execute("delete from meeting where id = " + model.getId());
			if (statement.getUpdateCount() > -1) {
				result = true;
			}
			connection.commit();
		} catch (SQLException ex) {

			throw new RuntimeException("Error deleting meeting.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}
		return result;
	}

	@Override
	public Collection<Meeting> searchByTeacher(Long id) {
		Connection connection = newConnection();

		Collection<Meeting> result = new LinkedList<>();

		try (ResultSet rs = connection.createStatement()
				.executeQuery("select * from meeting where teacher_id = " + id)) {

			while (rs.next()) {
				result.add(exctractMeeting(rs));
			}
			connection.commit();
		} catch (SQLException ex) {

			throw new RuntimeException("Error getting employees.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}
		return result.isEmpty() ? null : result;
	}

	@Override
	public Collection<Meeting> searchByCity(String query) {
		if (query == null) {
			query = "";
		} else {
			query = query.trim();
		}

		Connection connection = newConnection();

		Collection<Meeting> result = new LinkedList<>();

		try (ResultSet rs = connection.createStatement()
				.executeQuery("select * from meeting where lower(city) like '%" + query.toLowerCase() + "%'")) {

			while (rs.next()) {
				result.add(exctractMeeting(rs));
			}
			connection.commit();
		} catch (SQLException ex) {

			throw new RuntimeException("Error getting meeting.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}

		return result;
	}

	@Override
	public Collection<Meeting> searchByDate(DateTime date) {
		Date sqlDate = new Date(System.currentTimeMillis());
		if (date != null) {
			sqlDate = (Date) date.toDate();
		} else {
			return getAll();
		}

		Connection connection = newConnection();

		Collection<Meeting> result = new LinkedList<>();

		try (ResultSet rs = connection.createStatement()
				.executeQuery("select * from meeting where meeting_date = " + sqlDate)) {

			while(rs.next()){
				result.add(exctractMeeting(rs));
			}
		} catch (SQLException ex) {
			throw new RuntimeException("Error getting meetings while searcing for date.", ex);
		}finally{
			try{
				connection.close();
			}catch(Exception ex){
				
			}
		}
		return result.isEmpty() ? null:result;
	}

	@Override
	public Collection<Meeting> searchByCourse(Course course) {
		Connection connection = newConnection();

		Collection<Meeting> result = new LinkedList<>();

		try (ResultSet rs = connection.createStatement()
				.executeQuery("select * from meeting where course_id = " + course.getId())) {

			while (rs.next()) {
				result.add(exctractMeeting(rs));
			}
			connection.commit();
		} catch (SQLException ex) {

			throw new RuntimeException("Error getting meetings while searcing by course.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}
		return result.isEmpty() ? null : result;
	}

	@Override
	public Collection<Meeting> searchByAttendee(Long id) {
		Connection connection = newConnection();

		Collection<Meeting> result = new LinkedList<>();

		try (ResultSet rs = connection.createStatement()
				.executeQuery("select * from meeting where course_id like '%" + id + "%'")) {

			while (rs.next()) {
				result.add(exctractMeeting(rs));
			}
			connection.commit();
		} catch (SQLException ex) {

			throw new RuntimeException("Error getting employees.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}
		return result.isEmpty() ? null : result;
	}

}
