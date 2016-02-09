package ro.sci.group2.service;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ro.sci.group2.ApplicationTest;
import ro.sci.group2.domain.Course;
import ro.sci.group2.domain.Gender;
import ro.sci.group2.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationTest.class)
public class UserServiceTest {

	@Autowired
	private UserService service;

	@After
	public void tearDown() {
		Collection<User> users = new ArrayList<User>(service.listAll());

		for (User user : users) {
			service.delete(user.getId());
		}
	}

	@Test
	public void testEmptyGetAll() {
		Collection<User> all = service.listAll();
		Assert.assertTrue(all.isEmpty());
	}

	@Test
	public void testFindById() throws ValidationException {
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("Becali");
		user.setFirstName("chmgngn");
		user.setLastName("hmghmjg");
		user.setAddress("gfmghjmfghfcjf");
		user.setEmail("");
		user.setPhone("1234569874");
		user.setGender(Gender.MALE);
		User savedUser = service.save(user);
		Assert.assertEquals(savedUser, service.findById(savedUser.getId()));
	}

	@Test
	public void testSaveNewUser() throws ValidationException {
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("Becali");
		user.setFirstName("ykfj");
		user.setLastName("fdjhgjg");
		user.setAddress("fdjjftujududr");
		user.setEmail("");
		user.setPhone("1236547896");
		user.setGender(Gender.MALE);
		User savedUser = service.save(user);
		Assert.assertTrue(savedUser.getId() > 0);
		Assert.assertEquals("Gigi", savedUser.getUsername());
		Assert.assertEquals("Becali", savedUser.getPassword());
	}

	@Test
	public void testSaveExistinguser() throws ValidationException {
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("Becali");
		user.setFirstName("dfjhd");
		user.setLastName("fghjfj");
		user.setAddress("dfjgksthdyrhgdr");
		user.setEmail("");
		user.setPhone("2316547896");
		user.setGender(Gender.MALE);
		User savedUser = service.save(user);
		Assert.assertTrue(savedUser.getId() > 0);
		User savedUser2 = service.save(user);
		Assert.assertEquals(savedUser, savedUser2);
		Assert.assertEquals(1, service.listAll().size());
	}

	@Test
	public void testDeleteUser() throws ValidationException {
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("Becali");
		user.setFirstName("hgjmhf");
		user.setLastName("hjkgfh");
		user.setAddress("dfhjdfhetgsgty");
		user.setEmail("");
		user.setPhone("1236985475");
		user.setGender(Gender.MALE);
		User savedUser = service.save(user);
		Assert.assertTrue(service.delete(savedUser.getId()));
		Assert.assertNull(service.findById(savedUser.getId()));
		Assert.assertEquals(0, service.listAll().size());
	}

	@Test
	public void testDoubleDeletionuser() throws ValidationException {
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("Becali");
		user.setFirstName("fdhrt");
		user.setLastName("hjkhgn");
		user.setAddress("fgjstgerytrhstg");
		user.setEmail("");
		user.setPhone("0321746985");
		user.setGender(Gender.MALE);
		User savedUser = service.save(user);
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
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("Becali");
		user.setFirstName("fhjfdht");
		user.setLastName("dtsfhyfdh");
		user.setAddress("fgjsgrtersyeshg");
		user.setEmail("");
		user.setPhone("1257489657");
		user.setGender(Gender.MALE);
		service.save(user);
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
	public void testDoubleAddCourse() throws ValidationException {
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("Becali");
		user.setFirstName("fgjfyj");
		user.setLastName("jhghf");
		user.setAddress("sthjjsrthtdhrdh");
		user.setEmail("");
		user.setPhone("1789645873");
		user.setGender(Gender.MALE);
		service.save(user);
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		service.addCourse(user.getId(), mate);
		service.addCourse(user.getId(), mate);
		Assert.assertTrue(user.getCourses().contains(mate));
		Assert.assertEquals(1, user.getCourses().size());
	}

	@Test
	public void testRemoveExistingCourse() throws ValidationException {
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("Becali");
		user.setFirstName("fhmff");
		user.setLastName("fykgujg");
		user.setAddress("fdjthetdghethet");
		user.setEmail("");
		user.setPhone("4785698753");
		user.setGender(Gender.MALE);
		service.save(user);
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
	public void testDoubleRemoveCourse() throws ValidationException {
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("Becali");
		user.setFirstName("ghmdfthd");
		user.setLastName("dshjfdth");
		user.setAddress("sfhjfjfdtgdhrh");
		user.setEmail("");
		user.setPhone("7852147569");
		user.setGender(Gender.MALE);
		service.save(user);
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		service.addCourse(user.getId(), mate);
		Assert.assertTrue(service.removeCourse(user.getId(), mate));
		Assert.assertFalse(service.removeCourse(user.getId(), mate));
	}

	@Test
	public void testRemoveInexistingCourse() throws ValidationException {
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("Becali");
		user.setFirstName("fdjfd");
		user.setLastName("klhhm");
		user.setAddress("fhdthrgertejrt");
		user.setEmail("");
		user.setPhone("6547854236");
		user.setGender(Gender.MALE);
		service.save(user);
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		Assert.assertFalse(service.removeCourse(user.getId(), mate));
	}

	@Test
	public void testUserOrdering() throws ValidationException {
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("becali");
		user.setFirstName("aaaaaa");
		user.setLastName("bbbbbbb");
		user.setAddress("shgsfgjfghvfrskg");
		user.setEmail("");
		user.setPhone("0746939983");
		user.setGender(Gender.MALE);
		User user2 = new User();
		user2.setUsername("Hagi");
		user2.setPassword("gheorghe");
		user2.setFirstName("ccccccc");
		user2.setLastName("ddddddd");
		user2.setAddress("shgsvfghjgfjgrskg");
		user2.setEmail("");
		user2.setPhone("0745939983");
		user2.setGender(Gender.MALE);
		service.save(user);
		service.save(user2);
		ArrayList<User> users = new ArrayList<>(service.listAll("firstascend"));
		Assert.assertEquals(user, users.get(0));
		Assert.assertEquals(user2, users.get(1));
		users = new ArrayList<>(service.listAll("firstdescend"));
		Assert.assertEquals(user, users.get(1));
		Assert.assertEquals(user2, users.get(0));
		users = new ArrayList<>(service.listAll("lastascend"));
		Assert.assertEquals(user, users.get(0));
		Assert.assertEquals(user2, users.get(1));
		users = new ArrayList<>(service.listAll("lastdescend"));
		Assert.assertEquals(user, users.get(1));
		Assert.assertEquals(user2, users.get(0));
	}

	@Test
	public void testAddMoreThanOneUser() throws ValidationException {
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("becali");
		user.setFirstName("aaaaaa");
		user.setLastName("bbbbbbb");
		user.setAddress("shgsfgjfghvfrskg");
		user.setEmail("");
		user.setPhone("0746939983");
		user.setGender(Gender.MALE);
		User user2 = new User();
		user2.setUsername("Hagi");
		user2.setPassword("gheorghe");
		user2.setFirstName("ccccccc");
		user2.setLastName("ddddddd");
		user2.setAddress("shgsvfghjgfjgrskg");
		user2.setEmail("");
		user2.setPhone("0745939983");
		user2.setGender(Gender.MALE);
		service.save(user);
		service.save(user2);
		Assert.assertTrue(2 == service.listAll().size());

	}

}
