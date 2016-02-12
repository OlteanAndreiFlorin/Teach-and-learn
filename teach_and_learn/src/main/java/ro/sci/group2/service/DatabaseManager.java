package ro.sci.group2.service;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.sci.group2.dao.CourseDAO;
import ro.sci.group2.dao.UserDAO;
import ro.sci.group2.domain.Course;
import ro.sci.group2.domain.Role;
import ro.sci.group2.domain.User;

/**
 * <p>
 * Database manager class manages collection of objects converting them into
 * strings
 * </p>
 * <p>
 * exemple:
 * </p>
 * <p>
 * A collection of roles({@link Role}}) to String;
 * </p>
 * <p>
 * or a Collection of Courses ({@link Course}) to a string containing the
 * Course's id's
 * </p>
 * 
 * @author Oltean Andrei
 *
 */
@Component
public class DatabaseManager {

	@Autowired
	private CourseDAO courseDao; // = new
									// JDBCCourseDAO("ec2-54-83-12-22.compute-1.amazonaws.com",
									// "5432", "d1vssoh84qkbg3",
									// "vacmpcjhlpcnft",
	// "6ZAEauN0X589o05fxrypEIl2v_");
	// new JDBCCourseDAO("localhost", "5432", "test", "test", "test");

	@Autowired
	private UserDAO userDao;

	public Collection<Role> convertStringToRole(String dbData) {
		Collection<Role> roles = new LinkedList<>();
		String[] r = dbData.split(";&;");
		for (String roleString : r) {
			roles.add(Role.valueOf((roleString)));
		}
		return roles;
	}

	public String convertRolesToString(Collection<Role> roles) {
		StringBuilder stringRole = new StringBuilder();
		for (Role r : roles) {
			stringRole.append(r.name() + ";&;");
		}
		return stringRole.toString();
	}

	public String convertCoursesToStringIds(Collection<Course> courses) {
		if (courses.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Course c : courses) {
			if (c.getId() > 0) {
				sb.append(c.getId());
				sb.append(";&;");
			} else {
				for (Course a : courseDao.getAll()) {
					if (c.equals(a)) {
						sb.append(a.getId());
						sb.append(";&;");
						break;
					}
				}
			}
		}
		return sb.toString();
	}

	public Collection<Course> convertStringToCourses(String dbCourses) {
		Collection<Course> courses = new LinkedList<>();
		if (dbCourses.isEmpty()) {
			return courses;
		}
		String[] s = dbCourses.split(";&;");
		for (String stringId : s) {
			Long id = Long.parseLong(stringId);
			if (courseDao.findById(id) == null) {
				throw new IllegalStateException("Coursen not found in db while converting courses");
			} else {
				courses.add(courseDao.findById(id));
			}
		}
		return courses;
	}

	public User findTeacher(Long id) {
		return userDao.findById(id);
	}

	public Course findCourse(long id) {
		return courseDao.findById(id);
	}

	public Collection<User> convertStringToUsers(String dbUsers) {
		Collection<User> users = new LinkedList<>();
		if(dbUsers.isEmpty()){
			return users;
		}
		String[] s = dbUsers.split(";&;");
		for(String stringId:s){
			Long id = Long.parseLong(stringId);
			if(userDao.findById(id) == null){
				throw new IllegalStateException("User not found in db while converting Users for meeting!");
			}else{
				users.add(userDao.findById(id));
			}
		}
		return users;
	}
}
