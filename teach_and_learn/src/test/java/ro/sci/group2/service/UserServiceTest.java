package ro.sci.group2.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ro.sci.group2.ApplicationTest;
import ro.sci.group2.domain.Course;
import ro.sci.group2.domain.Gender;
import ro.sci.group2.domain.Role;
import ro.sci.group2.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationTest.class)
public class UserServiceTest {

	@Autowired
	private UserService service;

	private User user;

	@Before
	public void setUp() {

		this.user = new User();
		this.user.setUsername("Gigi");
		this.user.setPassword("Becali5!");
		this.user.setFirstName("chmgngn");
		this.user.setLastName("hmghmjg");
		this.user.setAddress("gfmghjmfghfcjf");
		this.user.setEmail("");
		this.user.setPhone("1234569874");
		this.user.setGender(Gender.MALE);
		Collection<Role> roles = new LinkedList<>();
		roles.add(Role.ROLE_ADMIN);
		roles.add(Role.ROLE_STUDENT);
		this.user.setRoles(roles);
	}

	@After
	public void tearDown() {
		Collection<User> users = new ArrayList<User>(service.listAll());

		for (User user : users) {
			service.delete(user.getId());
		}
	}
	
	@Test
	public void testAdminCreateUser() throws ValidationException{
		User u = new User();
		u.setUsername("admin");
		u.setPassword("admin!1");
		Collection<Role> rol=new LinkedList<Role>();
		rol.add(Role.ROLE_ADMIN);
		u.setRoles(rol);
		service.save(u);
	}

	@Test
	public void testEmptyGetAll() {
		Collection<User> all = service.listAll();
		Assert.assertTrue(all.isEmpty());
	}

	@Test
	public void testFindById() throws ValidationException {
		User savedUser = service.save(this.user);
		Assert.assertEquals(savedUser, service.findById(savedUser.getId()));
	}

	@Test
	public void testSaveNewUser() throws ValidationException {
		User savedUser = service.save(this.user);
		Assert.assertTrue(savedUser.getId() > 0);
		Assert.assertEquals("Gigi", savedUser.getUsername());
		Assert.assertEquals("Becali5!", savedUser.getPassword());
	}

	@Test
	public void testSaveExistinguser() throws ValidationException {
		User savedUser = service.save(this.user);
		Assert.assertTrue(savedUser.getId() > 0);
		User savedUser2 = service.save(user);
		Assert.assertEquals(savedUser, savedUser2);
		Assert.assertEquals(1, service.listAll().size());
	}

	@Test
	public void testDeleteUser() throws ValidationException {
		User savedUser = service.save(this.user);
		Assert.assertTrue(service.delete(savedUser.getId()));
		Assert.assertNull(service.findById(savedUser.getId()));
		Assert.assertEquals(0, service.listAll().size());
	}

	@Test
	public void testDoubleDeletionuser() throws ValidationException {
		User savedUser = service.save(this.user);
		Assert.assertTrue(service.delete(savedUser.getId()));
		Assert.assertFalse(service.delete(savedUser.getId()));
		Assert.assertNull(service.findById(savedUser.getId()));
		Assert.assertEquals(0, service.listAll().size());
	}

	@Test
	public void testDeleteInexistinguser() {
		Assert.assertFalse(service.delete(-1));
	}

	@Test
	public void testAddCourse() throws ValidationException {
		service.save(this.user);
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		service.addCourse(this.user.getId(), mate);
		User mock = service.findById(this.user.getId());
		Assert.assertTrue(mock.getCourses().contains(mate));
		Course romana = new Course();
		romana.setName("romana");
		romana.setLevel(5);
		service.addCourse(this.user.getId(), romana);
		mock =service.findById(this.user.getId());
		Assert.assertTrue(mock.getCourses().contains(romana));
		Assert.assertEquals(2, mock.getCourses().size());
	}

	@Test
	public void testDoubleAddCourse() throws ValidationException {
		service.save(this.user);
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		service.addCourse(this.user.getId(), mate);
		service.addCourse(this.user.getId(), mate);
		User mock = service.findById(this.user.getId());
		Assert.assertTrue(mock.getCourses().contains(mate));
		Assert.assertEquals(1, mock.getCourses().size());
	}

	@Test
	public void testRemoveExistingCourse() throws ValidationException {
		service.save(this.user);
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		Course romana = new Course();
		romana.setName("romana");
		romana.setLevel(5);
		service.addCourse(this.user.getId(), mate);
		service.addCourse(this.user.getId(), romana);
		User mock = service.findById(this.user.getId());
		Assert.assertTrue(mock.getCourses().contains(mate));
		Assert.assertTrue(service.removeCourse(this.user.getId(), mate));
		mock = service.findById(this.user.getId());
		Assert.assertEquals(1, mock.getCourses().size());
		Assert.assertTrue(service.removeCourse(this.user.getId(), romana));
		mock = service.findById(this.user.getId());
		Assert.assertTrue(mock.getCourses().isEmpty());
	}

	@Test
	public void testDoubleRemoveCourse() throws ValidationException {
		service.save(this.user);
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		service.addCourse(this.user.getId(), mate);
		Assert.assertTrue(service.removeCourse(this.user.getId(), mate));
		Assert.assertFalse(service.removeCourse(this.user.getId(), mate));
	}

	@Test
	public void testRemoveInexistingCourse() throws ValidationException {
		service.save(this.user);
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		Assert.assertFalse(service.removeCourse(this.user.getId(), mate));
	}

	@Test
	public void testUserOrdering() throws ValidationException {
		User user2 = new User();
		user2.setUsername("Hagi");
		user2.setPassword("gheorghe@1");
		user2.setFirstName("ccccccc");
		user2.setLastName("ddddddd");
		user2.setAddress("shgsvfghjgfjgrskg");
		user2.setEmail("");
		user2.setPhone("0745939983");
		user2.setGender(Gender.MALE);
		Collection<Role> roles2 = new LinkedList<>();
		roles2.add(Role.ROLE_ADMIN);
		roles2.add(Role.ROLE_STUDENT);
		user2.setRoles(roles2);
		service.save(this.user);
		service.save(user2);
		ArrayList<User> users = new ArrayList<>(service.listAll("firstascend"));
		Assert.assertEquals(this.user, users.get(1));
		Assert.assertEquals(user2, users.get(0));
		users = new ArrayList<>(service.listAll("firstdescend"));
		Assert.assertEquals(this.user, users.get(0));
		Assert.assertEquals(user2, users.get(1));
		users = new ArrayList<>(service.listAll("lastascend"));
		Assert.assertEquals(this.user, users.get(1));
		Assert.assertEquals(user2, users.get(0));
		users = new ArrayList<>(service.listAll("lastdescend"));
		Assert.assertEquals(this.user, users.get(0));
		Assert.assertEquals(user2, users.get(1));
	}

	@Test
	public void testAddMoreThanOneUser() throws ValidationException {
		User user2 = new User();
		user2.setUsername("Hagi");
		user2.setPassword("gheorghe5@");
		user2.setFirstName("ccccccc");
		user2.setLastName("ddddddd");
		user2.setAddress("shgsvfghjgfjgrskg");
		user2.setEmail("");
		user2.setPhone("0745939983");
		user2.setGender(Gender.MALE);
		Collection<Role> roles2 = new LinkedList<>();
		roles2.add(Role.ROLE_ADMIN);
		roles2.add(Role.ROLE_STUDENT);
		user2.setRoles(roles2);
		service.save(this.user);
		service.save(user2);
		Assert.assertTrue(2 == service.listAll().size());

	}

}
