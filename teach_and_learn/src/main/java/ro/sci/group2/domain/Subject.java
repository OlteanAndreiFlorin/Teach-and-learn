/**
 * 
 */
package ro.sci.group2.domain;

/**
 * Class which is modeling a subject (science to be taught).
 * <p> It is described by its {@link String subjectName parameter}
 * <p> by a {@link Integer subjectLevel} (allowed: from 1st grade to 12th grade) - which is optional
 * <p> and by a {@link Integer subjectMaxAttendance} - the maximum number (>=1, <=100) of {@link Student students} to be enrolled    
 * <p> It holds two constructors, one with just the subjectName and subjectMaxAttendance
 * <p> and the other with all the three parameters.
 * <p> Examples :
 * <p> - Subject robots = new Subject("Industrial Robots" , 30) as the matter is rather general
 * <p> - Subject mathFour = new Subject("Math" , 4 , 10) for Math addressed to 4th grade pupils
 * <p> The subject name cannot be changed, a new instance has to be created instead
 * @author Razvan Radu
 *
 * 
 */
public class Subject {

	private String subjectName;
	private int subjectLevel;
	private int subjectMaxAttendance;

	/**
	 * 
	 * @param subjectName {@link String string} representing the name of the subject to be taught
	 * @param subjectMaxAttendance {@link Integer integer number} representing the maximum number of participants (excluding the teacher) to this meeting
	 * @throws IllegalArgumentException in case the {@link Integer maximum number of attendants} is smaller than 1 or larger than 100.
	 */
	public Subject(String subjectName , int subjectMaxAttendance) throws IllegalArgumentException{
		if ((subjectMaxAttendance < 1) || (subjectMaxAttendance > 100)){
			throw new IllegalArgumentException("Invalid data - number of attendans allowed is 1 .. 100");
		}
		else if ((null == subjectName)){
			throw new IllegalArgumentException("Name cannot be blank");
		}
		else {
			this.subjectName=subjectName;
			this.subjectMaxAttendance=subjectMaxAttendance;
		}
	}
	/**
	 * 
	 * @param subjectName {@link String string type } containing the name of the subject to be taught
	 * @param subjectLevel 
	 * @param subjectMaxAttendance
	 * @throws IllegalArgumentException in case the subjectName is blank, the subjectLevel is out of limits (1<= subjectLevel <=12) or
	 * <p> the subjectMaxAttendance is out of limits (1<= subjectMaxAttendance <=100)
	 */
	public Subject(String subjectName , int subjectLevel , int subjectMaxAttendance) throws IllegalArgumentException{
		if ((subjectLevel < 1) || (subjectLevel > 12)){
			throw new IllegalArgumentException("Invalid data - level must be representing a class (1-12)");
		}
		if ((subjectMaxAttendance < 1) || (subjectMaxAttendance > 100)){
			throw new IllegalArgumentException("Invalid data - number of attendans allowed is 1 .. 100");
		}
		else if ((null == subjectName)){
			throw new IllegalArgumentException("Name cannot be blank");
		}
		else {
			this.subjectName=subjectName;
			this.subjectLevel=subjectLevel;
			this.subjectMaxAttendance=subjectMaxAttendance;
		}
	}

	/**
	 * @return the {@link Integer subjectLevel}
	 */
	public int getSubjectLevel() {
		return subjectLevel;
	}

	/**
	 * throws an {@link IllegalArgumentException exception} is the {@link Integer subjectLevel} if out of bounds (1<= subjectLevel <=12) 
	 * @param subjectLevel the {@link Integer subjectLevel} to set (integer value from 1 to 12 allowed)
	 *        
	 */
	public void setSubjectLevel(int subjectLevel) {
		if ((subjectLevel >= 1) && (subjectLevel <= 12)) {
			this.subjectLevel = subjectLevel;
		}
		else {
			throw new IllegalArgumentException("Wrong subject level, from 1 to 12 allowed");
		}
	}

	/**
	 * @return the {@link Integer subjectMaxAttendance} (integer value from 1 to 100)
	 */
	public int getSubjectMaxAttendance() {
		return subjectMaxAttendance;
	}

	/**
	 * throws an {@link IllegalArgumentException exception} is the {@link Integer subjectMaxAttendance} if out of bounds (1<= subjectMaxAttendance <=100) 
	 * @param subjectMaxAttendance
	 *            the subjectMaxAttendance to set
	 */
	public void setSubjectMaxAttendance(int subjectMaxAttendance) {
		if ((subjectMaxAttendance >= 1) && (subjectMaxAttendance <= 100)) {
			this.subjectMaxAttendance = subjectMaxAttendance;
		}
		else {
			throw new IllegalArgumentException("Wrong subject max attendance, from 1 to 100 allowed");
		}
	}
	
	/**
	 * @return the subjectName, String representing the name of the matter/subject to be taught
	 */
	public String getSubjectName() {
		return subjectName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + subjectLevel;
		result = prime * result + subjectMaxAttendance;
		result = prime * result + ((subjectName == null) ? 0 : subjectName.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subject other = (Subject) obj;
		if (subjectLevel != other.subjectLevel)
			return false;
		if (subjectMaxAttendance != other.subjectMaxAttendance)
			return false;
		if (subjectName == null) {
			if (other.subjectName != null)
				return false;
		} else if (!subjectName.equals(other.subjectName))
			return false;
		return true;
	}

	
}
