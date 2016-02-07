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
import ro.sci.group2.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TeachAndLearnApplication.class)
public class UserServiceTest {

	@Autowired
	private UserService service;

	@After
	public void tearDown() {
		for (User user : service.listAll()) {
			service.delete(user.getId());
		}
	}

	@Test
	public void testSaveNewuser() {
		User user = new User();
		user.setFirstName("Gigi");
		user.setLastName("Becali");
		User savedUser = service.save(user);
		Assert.assertTrue(savedUser.getId() > 0);
		Assert.assertEquals("Gigi", savedUser.getFirstName());
		Assert.assertEquals("Becali", savedUser.getLastName());
	}

	@Test
	public void testSaveExistinguser() {
		User user = new User();
		user.setFirstName("Gigi");
		user.setLastName("Becali");
		//user.setUsername("moni");
		User savedUser = service.save(user);
		Assert.assertTrue(savedUser.getId() > 0);
		User savedUser2 = service.save(user);
		Assert.assertEquals(savedUser, savedUser2);
	}

	@Test
	public void testDeleteuser() {
		User user = new User();
		user.setFirstName("Gigi");
		user.setLastName("Becali");
		User savedUser = service.save(user);
		Assert.assertTrue(service.delete(savedUser.getId()));
		Assert.assertNull(service.findById(savedUser.getId()));
	}

	@Test
	public void testDoubleDeletionuser() {
		User user = new User();
		user.setFirstName("Gigi");
		user.setLastName("Becali");
		User savedUser = service.save(user);
		Assert.assertTrue(service.delete(savedUser.getId()));
		Assert.assertFalse(service.delete(savedUser.getId()));
		Assert.assertNull(service.findById(savedUser.getId()));
	}

	@Test
	public void testDeleteInexistinguser() {
		Assert.assertFalse(service.delete(-1));
	}

	@Test
	public void testAddCourse() {
		User user = new User();
		service.save(user);
		user.setFirstName("Gigi");
		user.setLastName("Becali");
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		service.addCourse(user.getId(), mate);
		Assert.assertTrue(user.getCourses().contains(mate));
		Course romana = new Course();
		romana.setName("romana");
		romana.setLevel(5);
		service.addCourse(user.getId(), romana);
		Assert.assertTrue(user.getCourses().contains(romana));
		Assert.assertEquals(2, user.getCourses().size());
	}

	@Test
	public void testDoubleAddCourse() {
		User user = new User();
		service.save(user);
		user.setFirstName("Gigi");
		user.setLastName("Becali");
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		service.addCourse(user.getId(), mate);
		service.addCourse(user.getId(), mate);
		Assert.assertTrue(user.getCourses().contains(mate));
		Assert.assertEquals(1, user.getCourses().size());
	}

	@Test
	public void testRemoveExistingCourse() {
		User user = new User();
		service.save(user);
		user.setFirstName("Gigi");
		user.setLastName("Becali");
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		Course romana = new Course();
		romana.setName("romana");
		romana.setLevel(5);
		service.addCourse(user.getId(), mate);
		service.addCourse(user.getId(), romana);
		Assert.assertTrue(user.getCourses().contains(mate));
		Assert.assertTrue(service.removeCourse(user.getId(), mate));
		Assert.assertEquals(1, user.getCourses().size());
		Assert.assertTrue(service.removeCourse(user.getId(), romana));
		Assert.assertTrue(user.getCourses().isEmpty());
	}
	@Test
	public void testDoubleRemoveCourse(){
		User user = new User();
		service.save(user);
		user.setFirstName("Gigi");
		user.setLastName("Becali");
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		service.addCourse(user.getId(), mate);
		Assert.assertTrue(service.removeCourse(user.getId(), mate));
		Assert.assertFalse(service.removeCourse(user.getId(), mate));
	}
	@Test
	public void testRemoveInexistingCourse(){
		User user = new User();
		service.save(user);
		user.setFirstName("Gigi");
		user.setLastName("Becali");
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		Assert.assertFalse(service.removeCourse(user.getId(), mate));
	}
}
