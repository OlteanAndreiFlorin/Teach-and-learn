package ro.sci.group2.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ro.sci.group2.TeachAndLearnApplication;
import ro.sci.group2.domain.Course;
import ro.sci.group2.domain.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TeachAndLearnApplication.class)
public class StudentServiceTest {

	@Autowired
	private StudentService service;

	@After
	public void tearDown() {
		for (Student student : service.listAll()) {
			service.delete(student.getId());
		}
	}

	@Test
	public void testSaveNewStudent() {
		Student student = new Student();
		student.setFirstName("Gigi");
		student.setLastName("Becali");
		Student savedStudent = service.save(student);
		Assert.assertTrue(savedStudent.getId() > 0);
		Assert.assertEquals("Gigi", savedStudent.getFirstName());
		Assert.assertEquals("Becali", savedStudent.getLastName());
	}

	@Test
	public void testSaveExistingStudent() {
		Student student = new Student();
		student.setFirstName("Gigi");
		student.setLastName("Becali");
		Student savedStudent = service.save(student);
		Assert.assertTrue(savedStudent.getId() > 0);
		Student savedStudent2 = service.save(student);
		Assert.assertEquals(savedStudent, savedStudent2);
	}

	@Test
	public void testDeleteStudent() {
		Student student = new Student();
		student.setFirstName("Gigi");
		student.setLastName("Becali");
		Student savedStudent = service.save(student);
		Assert.assertTrue(service.delete(savedStudent.getId()));
		Assert.assertNull(service.findById(savedStudent.getId()));
	}

	@Test
	public void testDoubleDeletionStudent() {
		Student student = new Student();
		student.setFirstName("Gigi");
		student.setLastName("Becali");
		Student savedStudent = service.save(student);
		Assert.assertTrue(service.delete(savedStudent.getId()));
		Assert.assertFalse(service.delete(savedStudent.getId()));
		Assert.assertNull(service.findById(savedStudent.getId()));
	}

	@Test
	public void testDeleteInexistingStudent() {
		Assert.assertFalse(service.delete(-1));
	}

	@Test
	public void testAddCourse() {
		Student student = new Student();
		service.save(student);
		student.setFirstName("Gigi");
		student.setLastName("Becali");
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		service.addCourse(student.getId(), mate);
		Assert.assertTrue(student.getDesiredCourses().contains(mate));
		Course romana = new Course();
		romana.setName("romana");
		romana.setLevel(5);
		service.addCourse(student.getId(), romana);
		Assert.assertTrue(student.getDesiredCourses().contains(romana));
		Assert.assertEquals(2, student.getDesiredCourses().size());
	}

	@Test
	public void testDoubleAddCourse() {
		Student student = new Student();
		service.save(student);
		student.setFirstName("Gigi");
		student.setLastName("Becali");
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		service.addCourse(student.getId(), mate);
		service.addCourse(student.getId(), mate);
		Assert.assertTrue(student.getDesiredCourses().contains(mate));
		Assert.assertEquals(1, student.getDesiredCourses().size());
	}

	@Test
	public void testRemoveExistingCourse() {
		Student student = new Student();
		service.save(student);
		student.setFirstName("Gigi");
		student.setLastName("Becali");
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		Course romana = new Course();
		romana.setName("romana");
		romana.setLevel(5);
		service.addCourse(student.getId(), mate);
		service.addCourse(student.getId(), romana);
		Assert.assertTrue(student.getDesiredCourses().contains(mate));
		Assert.assertTrue(service.removeCourse(student.getId(), mate));
		Assert.assertEquals(1, student.getDesiredCourses().size());
		Assert.assertTrue(service.removeCourse(student.getId(), romana));
		Assert.assertTrue(student.getDesiredCourses().isEmpty());
	}
	@Test
	public void testDoubleRemoveCourse(){
		Student student = new Student();
		service.save(student);
		student.setFirstName("Gigi");
		student.setLastName("Becali");
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		service.addCourse(student.getId(), mate);
		Assert.assertTrue(service.removeCourse(student.getId(), mate));
		Assert.assertFalse(service.removeCourse(student.getId(), mate));
	}
	@Test
	public void testRemoveInexistingCourse(){
		Student student = new Student();
		service.save(student);
		student.setFirstName("Gigi");
		student.setLastName("Becali");
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		Assert.assertFalse(service.removeCourse(student.getId(), mate));
	}
}
