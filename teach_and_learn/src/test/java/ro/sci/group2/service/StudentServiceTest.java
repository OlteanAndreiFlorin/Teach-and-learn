package ro.sci.group2.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ro.sci.group2.TeachAndLearnApplication;
import ro.sci.group2.domain.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TeachAndLearnApplication.class)
public class StudentServiceTest {

	@Autowired
	private StudentService service;

	@Test
	public void testSaveNewStudent() {
		Student student = new Student();
		student.setFirstName("Gigi");
		student.setLastName("Beeeeecali");
		Student savedStudent = service.save(student);
		Assert.assertTrue(savedStudent.getId() > 0);
		Assert.assertEquals("Gigi", savedStudent.getFirstName());
		Assert.assertEquals("Beeeeecali", savedStudent.getLastName());
	}

	@Test
	public void testSaveExistingStudent() {
		Student student = new Student();
		student.setFirstName("Gigi");
		student.setLastName("Beeeeecali");
		Student savedStudent = service.save(student);
		Assert.assertTrue(savedStudent.getId() > 0);
		Student savedStudent2 = service.save(student);
		Assert.assertEquals(savedStudent, savedStudent2);
	}

}
