/**
 * 
 */
package ro.sci.group2.domain;

/**
 * Class which is modeling a subject (science to be taught).
 * <p>
 * 
 * @author Razvan Radu
 *
 */
public class Subject {

	private String subjectName;
	private int subjectLevel;
	private int subjectMaxAttendance;

	/**
	 * 
	 * @param subjectName
	 * @param subjectMaxAttendance
	 * @throws IllegalArgumentException
	 */
	public Subject(String subjectName , int subjectMaxAttendance) throws IllegalArgumentException{
		if ((subjectMaxAttendance < 1) || (subjectMaxAttendance > 100)){
			throw new IllegalArgumentException("Invalid data - number of attendans allowed is 1 .. 100");
		}
		else {
			this.subjectName=subjectName;
			this.subjectMaxAttendance=subjectMaxAttendance;
		}
	}
	/**
	 * 
	 * @param subjectName {@link String string type containing the name of the subject to be taught
	 * @param subjectLevel 
	 * @param subjectMaxAttendance
	 * @throws IllegalArgumentException
	 */
	public Subject(String subjectName , int subjectLevel , int subjectMaxAttendance) throws IllegalArgumentException{
		if ((subjectLevel < 0) || (subjectLevel > 12)){
			throw new IllegalArgumentException("Invalid data - level must be representing a class (1-12)");
		}
		if ((subjectMaxAttendance < 1) || (subjectMaxAttendance > 100)){
			throw new IllegalArgumentException("Invalid data - number of attendans allowed is 1 .. 100");
		}
		else {
			this.subjectName=subjectName;
			this.subjectLevel=subjectLevel;
			this.subjectMaxAttendance=subjectMaxAttendance;
		}
	}

	/**
	 * @return the subjectLevel
	 */
	public int getSubjectLevel() {
		return subjectLevel;
	}

	/**
	 * @param subjectLevel
	 *            the subjectLevel to set
	 */
	public void setSubjectLevel(int subjectLevel) {
		this.subjectLevel = subjectLevel;
	}

	/**
	 * @return the subjectMaxAttendance
	 */
	public int getSubjectMaxAttendance() {
		return subjectMaxAttendance;
	}

	/**
	 * @param subjectMaxAttendance
	 *            the subjectMaxAttendance to set
	 */
	public void setSubjectMaxAttendance(int subjectMaxAttendance) {
		this.subjectMaxAttendance = subjectMaxAttendance;
	}
	
	/**
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

}
