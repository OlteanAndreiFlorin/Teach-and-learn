package ro.sci.group2.web;

import java.util.Collection;
import java.util.LinkedList;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
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
import ro.sci.group2.service.ValidationException;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	UserService userService;
	@Autowired
	CourseService courseService;
	@Autowired
	MeetingService meetingService;

	@RequestMapping("")
	public ModelAndView list(Long id, @CurrentUser org.springframework.security.core.userdetails.User u) {
		ModelAndView view = new ModelAndView("teacher_index");
		view.addObject("user", userService.findByUserName(u.getUsername()));
		return view;
	}

	@RequestMapping("/teacher_profile")
	public ModelAndView onEdit(Long id, @CurrentUser org.springframework.security.core.userdetails.User u) {
		ModelAndView result;

		if (u.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TEACHER"))) {
			result = new ModelAndView("teacher_profile");
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

	@RequestMapping("/teacher_meetings")
	public ModelAndView listMeetings(Long id) {
		ModelAndView view = new ModelAndView("teacher_meetings");
		User user = new User();
		if (id != null) {
			user = userService.findById(id);
		}
		Collection<Meeting>listOfMeetings = meetingService.searchByTeacher(id);
		for (Meeting meet : listOfMeetings) {
			meet.setTeacher(user);
			meetingService.save(meet);
		}
		view.addObject("user", user);
		view.addObject("meetings", meetingService.searchByTeacher(id));
		return view;
	}
	
	@RequestMapping("/meeting_details")
	public ModelAndView meetingDetail(Long id) {
		ModelAndView view = new ModelAndView("teacher_meeting_details");
		Meeting meet = meetingService.findById(id);
		Collection<User>listOfUsers = meet.getAttendees();
		view.addObject("attendees", listOfUsers);
		view.addObject("meeting", meet);
		return view;
	}
	
	@RequestMapping("/meeting_edit")
	public ModelAndView onMeetingEdit(Long id, @CurrentUser org.springframework.security.core.userdetails.User u) {
		ModelAndView result = new ModelAndView("teacher_meeting_edit");
		Meeting meeting = new Meeting();
		User user = new User();
		if (id != null) {
			meeting = meetingService.findById(id);
			user = userService.findById(meeting.getTeacher().getId());
		}
		result.addObject("user", user);
		result.addObject("teacherCourses", user.getCourses());
		result.addObject("meeting", meeting);
		return result;
	}
	
	@RequestMapping(value="/meeting_edit" , method = RequestMethod.POST)
	public ModelAndView saveEditedMeeting(Long id , Long courseId , Long userId , String city , String location , String meetingInterval , String duration , String observation , int maxAttendance , @CurrentUser org.springframework.security.core.userdetails.User u) {
		String pattern = "YYYY-MM-DD HH:mm";
		String pattern2 = "HH:mm";

		User user = new User();
		if (userId != null) {
			user = userService.findById(userId);
		}
		DateTime localDateTime = DateTime.parse(meetingInterval, DateTimeFormat.forPattern(pattern));
		DateTime localDuration = DateTime.parse(duration, DateTimeFormat.forPattern(pattern2));
		Meeting meeting = meetingService.findById(id);
		meeting.setTeacher(user);
		meeting.setCity(city);
		meeting.setLocation(location);
		meeting.setMeetingInterval(localDateTime);
		meeting.setDuration(localDuration);
		meeting.setObservation(observation);
		meeting.setMaxAttendance(maxAttendance);
		meetingService.save(meeting);
		ModelAndView view = new ModelAndView("teacher_meetings");
		view.addObject("user", user);
		view.addObject("meetings", meetingService.searchByTeacher(user.getId()));
		return view;
	}
	
	@RequestMapping("/meeting_create")
	public ModelAndView onMeetingCreate(Long id) {
		ModelAndView result = new ModelAndView("teacher_meeting_create");
		User user = new User();
		if (id != null) {
			user = userService.findById(id);
		}
		result.addObject("teacherCourses", user.getCourses());
		result.addObject("user" , user);
		return result;
	}
	
	@RequestMapping(value="/meeting_create" , method = RequestMethod.POST)
	public ModelAndView saveMeeting(Long courseId , Long userId , String city , String location , String meetingInterval , String duration , String observation , int maxAttendance , @CurrentUser org.springframework.security.core.userdetails.User u) {
		String pattern = "YYYY-MM-DD HH:mm";
		String pattern2 = "HH:mm";

		User user = new User();
		if (userId != null) {
			user = userService.findById(userId);
		}
		DateTime localDateTime = DateTime.parse(meetingInterval, DateTimeFormat.forPattern(pattern));
		DateTime localDuration = DateTime.parse(duration, DateTimeFormat.forPattern(pattern2));
		Meeting meeting = new Meeting();
		Course course = courseService.findById(courseId);
		meeting.setTeacher(user);
		meeting.setCourse(course);
		meeting.setCity(city);
		meeting.setLocation(location);
		meeting.setMeetingInterval(localDateTime);
		meeting.setDuration(localDuration);
		meeting.setObservation(observation);
		meeting.setMaxAttendance(maxAttendance);
		Collection<User>attendees = new LinkedList<User>();
		meeting.setAttendees(attendees);
		meetingService.save(meeting);
		ModelAndView view = new ModelAndView("teacher_meetings");
		view.addObject("user", user);
		view.addObject("meetings", meetingService.searchByTeacher(user.getId()));
		return view;
	}
	
	@RequestMapping("/meeting_delete")
	public ModelAndView onDelete(long id , Long userId) {
		if (!meetingService.delete(id)) {
			throw new IllegalStateException("Non existing user");
		} else {
			return listMeetings(userId);
		}
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView saveuser(User user, @CurrentUser org.springframework.security.core.userdetails.User u) throws ValidationException {
		user.setUsername(u.getUsername());
		Collection<Role> roles = new LinkedList<>();
		for (GrantedAuthority auth : u.getAuthorities()) {
			roles.add(Role.valueOf(auth.getAuthority()));
		}
		user.setRoles(roles);
		userService.save(user);
		ModelAndView view = new ModelAndView("teacher_index");
		view.addObject(user);
		return view;
	}

	@RequestMapping("/teacher_courses")
	public ModelAndView listCourses(Long id, @CurrentUser org.springframework.security.core.userdetails.User u) {
		ModelAndView view = new ModelAndView("teacher_courses");
		User user = new User();
		if (id != null) {
			user = userService.findById(id);
		}
		view.addObject("user", user);
		view.addObject("teacherCourses", user.getCourses());
		view.addObject("baseCourses", courseService.listAll());
		return view;
	}

	@RequestMapping(value = "/teacher_courses_add")
	public ModelAndView addCourse(Long id,
			@CurrentUser org.springframework.security.core.userdetails.User u) {
		ModelAndView view = new ModelAndView("teacher_courses");
		User user = new User();
		if (id != null) {
			user = userService.findByUserName(u.getUsername());
			userService.addCourse(user.getId(), courseService.findById(id));
		}
		view.addObject("user", user);
		view.addObject("teacherCourses", user.getCourses());
		view.addObject("baseCourses", courseService.listAll());
		return view;
	}

	@RequestMapping(value = "/teacher_course_create")
	public ModelAndView onCreate(Long id) {

		ModelAndView view = new ModelAndView("new_course");
		User user = new User();
		if (id != null) {
			user = userService.findById(id);
		}
		view.addObject("user", user);
		return view;
	}

	@RequestMapping(value = "/teacher_course_create", method = RequestMethod.POST)
	public ModelAndView saveCourse(Long id , Course course, @CurrentUser org.springframework.security.core.userdetails.User u) {
		courseService.save(course);
		ModelAndView view = new ModelAndView("teacher_courses");
		User user = new User();
		user = userService.findByUserName(u.getUsername());
		view.addObject("user", user);
		view.addObject("teacherCourses", user.getCourses());
		view.addObject("baseCourses", courseService.listAll());
		return view;
	}

	@RequestMapping(value = "/teacher_course_remove")
	public ModelAndView removeCourse(Long id,
			@CurrentUser org.springframework.security.core.userdetails.User u) {
		ModelAndView view = new ModelAndView("teacher_courses");
		User user = new User();
		if (id != null) {
			user = userService.findByUserName(u.getUsername());
			userService.removeCourse(user.getId(), courseService.findById(id));
		}
		view.addObject("user", user);
		view.addObject("teacherCourses", user.getCourses());
		view.addObject("baseCourses", courseService.listAll());
		return view;
	}

}
