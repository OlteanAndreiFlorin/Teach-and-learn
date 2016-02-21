package ro.sci.group2.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
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
import ro.sci.group2.domain.Meeting;
import ro.sci.group2.domain.Role;
import ro.sci.group2.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationTest.class)

public class MeetingServiceTest {

	@Autowired
	private CourseService courseService;

	@Autowired
	private MeetingService service;
	private Meeting meeting = new Meeting();

	@Autowired
	private UserService userService;
	private User savedUser = new User();

	@Before
	public void setUp() throws ValidationException {
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("Becali5!");
		user.setFirstName("chmgngn");
		user.setLastName("hmghmjg");
		user.setAddress("gfmghjmfghfcjf");
		user.setEmail("");
		user.setPhone("1234569874");
		user.setGender(Gender.MALE);
		Collection<Role> roles = new LinkedList<>();
		roles.add(Role.ROLE_TEACHER);
		user.setRoles(roles);
		Course romana = new Course();
		romana.setLevel(10);
		romana.setName("romana");
		Course savedCourse = courseService.save(romana);
		Collection<Course> courses = new LinkedList<Course>();
		courses.add(savedCourse);
		user.setCourses(courses);
		this.savedUser = userService.save(user);
		meeting.setCity("Cluj");
		meeting.setCourse(savedCourse);
		DateTime duration = DateTime.parse("01:00", DateTimeFormat.forPattern("HH:mm"));
		meeting.setDuration(duration);
		DateTime date = DateTime.parse("2020-05-12 01:00", DateTimeFormat.forPattern("YYYY-MM-DD HH:mm"));
		meeting.setMeetingDate(date);
		meeting.setTeacher(savedUser);
		meeting.setMaxAttendance(12);
	}

	@After
	public void cleanUp() {
		Collection<Meeting> meetings = new ArrayList<>(service.listAll());
		for (Meeting m : meetings) {
			service.delete(m.getId());
		}
		userService.delete(savedUser.getId());
	}

	@Test
	public void testEmptyListAll() {
		service.listAll();
	}

	@Test
	public void testSaveNewMeeting() {
		Meeting savedMeeting = service.save(meeting);
		System.out.println(service.findById(savedMeeting.getId()).getMeetingDate());
		System.out.println(savedMeeting.getMeetingDate());
		Assert.assertEquals(savedMeeting, service.findById(savedMeeting.getId()));
		Assert.assertEquals(1, service.listAll().size());
	}

	@Test
	public void testDoubleSaveMeetings() {
		Meeting savedMeeting = service.save(meeting);
		Assert.assertEquals(savedMeeting, service.findById(savedMeeting.getId()));
		Meeting savedMeeting2 = service.save(meeting);
		Assert.assertEquals(savedMeeting2, service.findById(savedMeeting.getId()));
		Assert.assertTrue(service.listAll().size() == 1);
	}

	@Test
	public void testDeleteMeeting() {
		Meeting savedMeeting = service.save(meeting);
		Assert.assertTrue(service.delete(savedMeeting.getId()));
		Assert.assertTrue(service.listAll().size() == 0);
		Assert.assertFalse(service.delete(savedMeeting.getId()));
	}

	@Test
	public void testFindById() {
		Meeting savedMeeting = service.save(meeting);
		Assert.assertEquals(savedMeeting, service.findById(savedMeeting.getId()));
		Assert.assertNull(service.findById(45));
	}

	@Test
	public void testSearchByTeacher() {
		Meeting savedMeeting = service.save(meeting);
		Assert.assertEquals(1, service.searchByTeacher(savedUser.getId()).size());
		LinkedList<Meeting> meetings = new LinkedList<>(service.searchByTeacher(this.savedUser.getId()));
		Assert.assertEquals(savedMeeting, meetings.get(0));
		service.delete(meeting.getId());
		Assert.assertNull(service.searchByTeacher(this.savedUser.getId()));
	}

	@Test
	public void testSearchByCity() {
		Meeting savedMeeting = service.save(meeting);
		Assert.assertEquals(1, service.searchByCity("Cluj").size());
		LinkedList<Meeting> meetings = new LinkedList<>(service.searchByCity("Cluj"));
		Assert.assertEquals(savedMeeting, meetings.get(0));
		service.delete(meeting.getId());
		Assert.assertEquals(0, service.searchByCity("Cluj").size());
	}

	@Test
	public void testSearchByDate() {
		Meeting savedMeeting = service.save(meeting);
		Assert.assertEquals(1, service.searchByDate(savedMeeting.getMeetingDate()).size());
		LinkedList<Meeting> meetings = new LinkedList<>(service.searchByDate(savedMeeting.getMeetingDate()));
		Assert.assertEquals(savedMeeting, meetings.get(0));
		service.delete(meeting.getId());
		Assert.assertNull(service.searchByDate(savedMeeting.getMeetingDate()));
	}
	
	@Test
	public void testSearchByCourse(){
		Meeting savedMeeting = service.save(meeting);
		Assert.assertEquals(1, service.searchByCourse(savedMeeting.getCourse()).size());
	}

}
