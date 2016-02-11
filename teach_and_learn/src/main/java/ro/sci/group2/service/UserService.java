package ro.sci.group2.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.sci.group2.dao.UserDAO;
import ro.sci.group2.domain.Course;
import ro.sci.group2.domain.User;

@Service
public class UserService {
	@Autowired
	private UserDAO dao;
	@Autowired
	private CourseService courseService;

	public User save(User user) throws ValidationException {
		validateUser(user);
		return dao.update(user);
	}

	private void validateUser(User user) throws ValidationException {
		List<String> errors = new LinkedList<String>();
		if (user.getUsername().length() < 4) {
			errors.add("Username to short! Should be at least 4 characters long");
		}
		if (user.getUsername().length() > 12) {
			errors.add("Username too long! Should be  less than 12 characters long");
		}
		if (user.getPassword().length() < 5) {
			errors.add("Password too short! Set it with at least 4 characters");
		}
		if (user.getPassword().length() > 15) {
			errors.add("Password too long! Set it with less than 12 characters");
		}
		boolean found = false;
		for (Character c : user.getPassword().toCharArray()) {
			if (Character.isDigit(c)) {
				found = true;
				break;
			} 
		}
		if(!found){
			errors.add("Password must contain at least one digit");
		}
		String[] symbols = { "!", "@", "#", "$", "%" };
		found = false;
		for (String s : symbols) {
			if (user.getPassword().contains(s)) {
				found = true;
				break;
			}
		}
		if (!found) {
			errors.add("Password must contain at least one of these symbols ! @ # $ % ");
		}
		if (user.getFirstName().length() < 2) {
			errors.add("First name should be at least 2 characters long");
		}
		if (user.getFirstName().length() > 20) {
			errors.add("First name should be at most 20 characters long");
		}
		if (user.getLastName().length() < 2) {
			errors.add("Last name should be at least 2 characters long");
		}
		if (user.getLastName().length() > 20) {
			errors.add("Last name should be at most 20 characters long");
		}
		for (char c : user.getPhone().toCharArray()) {
			if (!Character.isDigit(c)) {
				errors.add("Invalid phone number.Phone should contain only digits");
			}
		}
		if (user.getPhone().length() < 10) {
			errors.add("Phone number incorrect! Should be at least 12 characters long");
		}
		if (user.getPhone().length() > 13) {
			errors.add("Phone number incorrect! It shouldn't be lnger than 14 characters");
		}
		if (user.getAddress().length() < 10) {
			errors.add("Are you sure you typed in the right address? It contains only 10 letters");
		}
		if (!errors.isEmpty()) {
			throw new ValidationException(errors.toArray(new String[] {}));
		}

	}

	public Collection<User> listAll() {
		return dao.getAll();
	}

	/**
	 * 
	 * @param order
	 *            the order in which to sort the list (must be "firstascend",
	 *            "firstdescend","lastascend"or"lastdescend")
	 * @return a sorted collection of users
	 * @throws IllegalArgumentException
	 *             if the order that has been passed is invalid
	 */
	public Collection<User> listAll(String order) throws IllegalArgumentException {
		UserSorter sorter = new UserSorter();
		switch (order.toLowerCase()) {
		case "firstascend":
			return sorter.sortByFirstNameAscending(dao.getAll());
		case "firstdescend":
			return sorter.sortByFirstNameDescending(dao.getAll());
		case "lastascend":
			return sorter.sortByLastNameAscending(dao.getAll());
		case "lastdescend":
			return sorter.sortByLastNameDescending(dao.getAll());
		default:
			throw new IllegalArgumentException("Order not valid ");
		}

	}

	public boolean delete(long id) {
		User user = dao.findById(id);
		if (user == null) {
			return false;
		} else {
			return dao.delete(user);
		}
	}

	public User findById(long id) {
		User user = dao.findById(id);
		return user;
	}

	public User findByUserName(String username) {
		User user = dao.findByUsername(username);
		return user;
	}

	public Collection<User> findByName(String query) {
		return dao.searchByName(query);
	}

	public void addCourse(long id, Course course) {
		User user = dao.findById(id);
		if (!courseService.listAll().contains(course)) {
			courseService.save(course);
		}
		Collection<Course> courses = new LinkedList<>(user.getCourses());
		if (!courses.contains(course)) {
			courses.add(course);
			user.setCourses(courses);
			try {
				save(user);
			} catch (ValidationException e) {
				e.printStackTrace();
			}
		}

	}

	public boolean removeCourse(long id, Course course) {
		User user = dao.findById(id);
		Collection<Course> courses = new LinkedList<>(user.getCourses());
		if (courses.remove(course)) {
			user.setCourses(courses);
			try {
				save(user);
			} catch (ValidationException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

}
