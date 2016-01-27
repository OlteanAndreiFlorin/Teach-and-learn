package ro.sci.group2.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import ro.sci.group2.domain.Student;

@Service
public class StudentSorter {

	private static final Comparator<Student> COMPARE_BY_FIRST_NAME_ASCENDING = new Comparator<Student>() {

		@Override
		public int compare(Student o1, Student o2) {

			return o1.getFirstName().toLowerCase().compareTo(o2.getFirstName().toLowerCase());
		}
	};
	private static final Comparator<Student> COMPARE_BY_FIRST_NAME_DESCENDING = new Comparator<Student>() {

		@Override
		public int compare(Student o1, Student o2) {

			return -o1.getFirstName().compareTo(o2.getFirstName());
		}
	};
	private static final Comparator<Student> COMPARE_BY_LAST_NAME_ASCENDING = new Comparator<Student>() {

		@Override
		public int compare(Student o1, Student o2) {

			return o1.getLastName().compareTo(o2.getLastName());
		}
	};
	private static final Comparator<Student> COMPARE_BY_LAST_NAME_DESCENDING = new Comparator<Student>() {

		@Override
		public int compare(Student o1, Student o2) {

			return -o1.getLastName().compareTo(o2.getLastName());
		}
	};
	/**
	 * 
	 * @param col a unsorted collection of students
	 * @return a sorted collection of students by first name ascending
	 */
	public Collection<Student> sortByFirstNameAscending(Collection<Student> col){
		List<Student> sortedCol = new LinkedList<>(col);
		Collections.sort(sortedCol,COMPARE_BY_FIRST_NAME_ASCENDING );
		return sortedCol;
	}
	/**
	 * 
	 * @param col a unsorted collection of students
	 * @return a sorted collection of students by last name ascending
	 */
	public Collection<Student> sortByLastNameAscending(Collection<Student> col){
		List<Student> sortedCol = new LinkedList<>(col);
		Collections.sort(sortedCol,COMPARE_BY_LAST_NAME_ASCENDING);
		return sortedCol;
	}
	/**
	 * 
	 * @param col a unsorted collection of students
	 * @return a sorted collection of students by first name descending
	 */
	public Collection<Student> sortByFirstNameDescending(Collection<Student> col){
		List<Student> sortedCol = new LinkedList<>(col);
		Collections.sort(sortedCol,COMPARE_BY_FIRST_NAME_DESCENDING );
		return sortedCol;
	}
	/**
	 * 
	 * @param col a unsorted collection of students
	 * @return a sorted collection of students by last name descending
	 */
	public Collection<Student> sortByLastNameDescending(Collection<Student> col){
		List<Student> sortedCol = new LinkedList<>(col);
		Collections.sort(sortedCol,COMPARE_BY_LAST_NAME_DESCENDING);
		return sortedCol;
	}
	public StudentSorter(){
		
	}

}
