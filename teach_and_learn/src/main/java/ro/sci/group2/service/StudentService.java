package ro.sci.group2.service;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.sci.group2.dao.StudentDao;
import ro.sci.group2.domain.Course;
import ro.sci.group2.domain.Student;

@Service
public class StudentService {
	@Autowired
	private StudentDao dao;

	public Student save(Student student) {
		return dao.update(student);
	}

	public Collection<Student> listAll() {
		return dao.getAll();
	}

	/**
	 * 
	 * @param order
	 *            the order in which to sort the list (must be "firstascend",
	 *            "firstdescend","lastascend"or"lastdescend")
	 * @return a sorted collection of students
	 * @throws IllegalArgumentException
	 *             if the order that has been passed is invalid
	 */
	public Collection<Student> listAll(String order) throws IllegalArgumentException {
		StudentSorter sorter = new StudentSorter();
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
		Student student = dao.findById(id);
		if (student == null) {
			return false;
		} else {
			return dao.delete(student);
		}
	}

	public Student findById(long id) {
		Student student = dao.findById(id);
		return student;
	}
	public Collection<Student> findByName(String query){
		return dao.searchByName(query);
	}

	public void addCourse(long id, Course course) {
		Student student = dao.findById(id);
		Collection<Course> courses = new LinkedList<>(student.getDesiredCourses());
		if (!courses.contains(course)) {
			courses.add(course);
			student.setDesiredCourses(courses);
		}
	}

	public boolean removeCourse(long id, Course course) {
		Student student = dao.findById(id);
		Collection<Course> courses = new LinkedList<>(student.getDesiredCourses());
		if (courses.remove(course)) {
			student.setDesiredCourses(courses);
			return true;
		} else {
			return false;
		}
	}

}
