/**
 * 
 */
package ro.sci.group2.domain;


import org.junit.Test;

/**
 * @author Razvan Radu
 *
 */
public class SubjectTest {
	
			
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithoutLevelInvalidName(){
		
		Subject testSubject = new Subject(null, 3);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithoutLevelInvalidAttendanceMin(){
		
		Subject testSubject = new Subject("Math", 0);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithoutLevelInvalidAttendanceMax(){
		
		Subject testSubject = new Subject("Math", 300);
	}
	
	@Test
	public void testConstructorWithoutLevelValid(){
		
		Subject testSubject = new Subject("Math", 100);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithLevelInvalidName(){
		
		Subject testSubject = new Subject(null, 3 , 3);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithLevelInvalidAttendanceMin(){
		
		Subject testSubject = new Subject("Math", 3 , 0);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithLevelInvalidAttendanceMax(){
		
		Subject testSubject = new Subject("Math", 3 , 300);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithLevelInvalidLevelMin(){
		
		Subject testSubject = new Subject("Math", 4 , -2);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithLevelInvalidLevelMax(){
		
		Subject testSubject = new Subject("Math", 20 , 10);
	}
	
	@Test
	public void testConstructorWithLevelValid(){
		
		Subject testSubject = new Subject("Math", 10 , 100);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetLevelInvalidMax(){
		
		Subject testSubject2 = new Subject("Math", 8 , 10);
		testSubject2.setSubjectLevel(20);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetLevelInvalidMin(){
		
		Subject testSubject2 = new Subject("Math", 8 , 10);
		testSubject2.setSubjectLevel(-4);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetAttendanceInvalidMax(){
		
		Subject testSubject2 = new Subject("Math", 8 , 10);
		testSubject2.setSubjectMaxAttendance(300);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetAttendanceInvalidMin(){
		
		Subject testSubject2 = new Subject("Math", 8 , 10);
		testSubject2.setSubjectMaxAttendance(-1);
	}
	
	@Test
	public void testSetValidValues(){
		
		Subject testSubject2 = new Subject("Math", 8 , 10);
		testSubject2.setSubjectLevel(9);
		testSubject2.setSubjectMaxAttendance(90);
	}
}
