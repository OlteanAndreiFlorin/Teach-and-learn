package ro.sci.group2.domain;

import java.util.Collection;
import java.util.LinkedList;

public class Student extends User {
	private String firstName;
	private String lastName;
	private String address;
	private Collection<Course> desiredCourses=new LinkedList<>();

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Collection<Course> getDesiredCourses() {
		return desiredCourses;
	}

	/*
	 * 
	 * @param course
	 * @throws NullPointerException if the course passed is null
	 
	public void addDesiredCourse(Course course)throws NullPointerException {
		if(!this.desiredCourses.contains(course)){
			this.desiredCourses.add(course);
		}
	}*/

	public void setDesiredCourses(Collection<Course> desiredCourses) {
		this.desiredCourses = desiredCourses;
	}

	public Student(long id) {
		setId(id);
		this.addRole(Role.STUDENT);
	}

	public Student() {
		this(0);
		this.addRole(Role.STUDENT);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

}
