package ro.sci.group2.web;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ro.sci.group2.annotation.CurrentUser;
import ro.sci.group2.domain.Course;
import ro.sci.group2.domain.Meeting;
import ro.sci.group2.domain.Role;
import ro.sci.group2.domain.User;
import ro.sci.group2.service.CourseService;
import ro.sci.group2.service.MeetingService;
import ro.sci.group2.service.UserService;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	UserService userService;
	@Autowired
	CourseService courseService;
	@Autowired
	MeetingService meetingService;

	@RequestMapping("")
	public ModelAndView list(Long id, @CurrentUser org.springframework.security.core.userdetails.User u) {
		ModelAndView view = new ModelAndView("student_index");
		view.addObject("user", userService.findByUserName(u.getUsername()));
		return view;
	}

	@RequestMapping("/student_profile")
	public ModelAndView onEdit(Long id, @CurrentUser org.springframework.security.core.userdetails.User u) {
		ModelAndView result;

		if (u.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"))) {
			result = new ModelAndView("student_profile");
		} else {
			result = new ModelAndView("index");
		}
		User user = new User();
		if (id != null) {
			user = userService.findById(id);
		}
		result.addObject("user", user);
		return result;
	}

	@RequestMapping("/student_meetings")
	public ModelAndView listMeetings(Long id) {

		ModelAndView view = new ModelAndView("student_meetings");
		User user = new User();
		if (id != null) {
			user = userService.findById(id);
		}
		view.addObject("user", user);
		Collection<Meeting> listOfMeetings = meetingService.listAll();
		for (Iterator<Meeting> it = listOfMeetings.iterator(); it.hasNext();) {
			Meeting meeting = it.next();
			Collection<User> attendees = meeting.getAttendees();
			if (!attendees.contains(user)) {
				it.remove();
			}
		}
		view.addObject("meetings",listOfMeetings);
		return view;
		}

	@RequestMapping("/student_available_meetings")
	public ModelAndView listAvailableMeetings(Long id) {

		ModelAndView view = new ModelAndView("student_available_meetings");
		User user = new User();
		if (id != null) {
			user = userService.findById(id);
		}
		view.addObject("user", user);
		Collection<Meeting> listOfMeetings = meetingService.listAll();
		Collection<Meeting> meetings = listOfMeetings;
		meetings.clear();
		Collection<Course> listOfCourses = user.getCourses();
		for (Iterator<Course> iter = listOfCourses.iterator(); iter.hasNext();) {
			Course course = iter.next();
			for (Iterator<Meeting> it = listOfMeetings.iterator(); it.hasNext();) {
				Meeting meeting = it.next();
				if (course.getId()==(meeting.getCourse().getId())) {
					meetings.add(meeting);
				}
			}
		}
		
		view.addObject("meetings", meetings);
		return view;
	}

	@RequestMapping("/meeting_join")
	public ModelAndView onMeetingJoin(Long id, @CurrentUser org.springframework.security.core.userdetails.User u) {

		Meeting meeting = new Meeting();
		User user = new User();
		if (id != null) {
			meeting = meetingService.findById(id);
			user = userService.findByUserName(u.getUsername());
			Collection<User> attendees = meeting.getAttendees();
			attendees.add(user);
			meeting.setAttendees(attendees);
		}
		return listMeetings(user.getId());
	}

	@RequestMapping("/meeting_leave")
	public ModelAndView onMeetingLeave(Long id, @CurrentUser org.springframework.security.core.userdetails.User u) {
		Meeting meeting = new Meeting();
		User user = new User();
		if (id != null) {
			meeting = meetingService.findById(id);
			user = userService.findByUserName(u.getUsername());
			Collection<User> attendees = meeting.getAttendees();
			attendees.remove(user);
			meeting.setAttendees(attendees);
		}
		return listMeetings(user.getId());
	}

	@RequestMapping("/meeting_details")
	public ModelAndView meetingDetail(Long id) {
		ModelAndView view = new ModelAndView("student_meeting_details");
		Meeting meet = meetingService.findById(id);
		view.addObject("user", meet.getTeacher());
		view.addObject("meeting", meet);
		return view;
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView saveuser(User user, @CurrentUser org.springframework.security.core.userdetails.User u) {
		user.setUsername(u.getUsername());
		Collection<Role> roles = new LinkedList<>();
		for (GrantedAuthority auth : u.getAuthorities()) {
			roles.add(Role.valueOf(auth.getAuthority()));
		}
		user.setRoles(roles);
		userService.save(user);
		ModelAndView view = new ModelAndView("student_index");
		view.addObject(user);
		return view;
	}

	@RequestMapping("/student_courses")
	public ModelAndView listCourses(Long id, @CurrentUser org.springframework.security.core.userdetails.User u) {
		ModelAndView view = new ModelAndView("student_courses");
		User user = new User();
		if (id != null) {
			user = userService.findById(id);
		}
		view.addObject("user", user);
		view.addObject("studentCourses", user.getCourses());
		view.addObject("baseCourses", courseService.listAll());
		return view;
	}

	@RequestMapping(value = "/student_courses_add")
	public ModelAndView addCourse(Long id, @CurrentUser org.springframework.security.core.userdetails.User u) {
		ModelAndView view = new ModelAndView("student_courses");
		User user = new User();
		if (id != null) {
			user = userService.findByUserName(u.getUsername());
			userService.addCourse(user.getId(), courseService.findById(id));
		}
		view.addObject("user", user);
		view.addObject("studentCourses", user.getCourses());
		view.addObject("baseCourses", courseService.listAll());
		return view;
	}

	@RequestMapping(value = "/student_course_create")
	public ModelAndView onCreate(Long id) {

		ModelAndView view = new ModelAndView("new_course");
		User user = new User();
		if (id != null) {
			user = userService.findById(id);
		}
		view.addObject("user", user);
		return view;
	}

	@RequestMapping(value = "/student_course_create", method = RequestMethod.POST)
	public ModelAndView saveCourse(Long id, Course course,
			@CurrentUser org.springframework.security.core.userdetails.User u) {
		courseService.save(course);
		ModelAndView view = new ModelAndView("student_courses");
		User user = new User();
		user = userService.findByUserName(u.getUsername());
		view.addObject("user", user);
		view.addObject("studentCourses", user.getCourses());
		view.addObject("baseCourses", courseService.listAll());
		return view;
	}

	@RequestMapping(value = "/student_course_remove")
	public ModelAndView removeCourse(Long id, @CurrentUser org.springframework.security.core.userdetails.User u) {
		ModelAndView view = new ModelAndView("student_courses");
		User user = new User();
		if (id != null) {
			user = userService.findByUserName(u.getUsername());
			userService.addCourse(user.getId(), courseService.findById(id));
		}
		view.addObject("user", user);
		view.addObject("studentCourses", user.getCourses());
		view.addObject("baseCourses", courseService.listAll());
		return view;
	}

}
