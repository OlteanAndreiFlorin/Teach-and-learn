package ro.sci.group2.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ro.sci.group2.annotation.CurrentUser;
import ro.sci.group2.domain.User;
import ro.sci.group2.service.UserService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	UserService userService;
	
	/*@RequestMapping("")
	public ModelAndView list(Long id, @CurrentUser org.springframework.security.core.userdetails.User u) {
		ModelAndView view = new ModelAndView("teacher_index");
		view.addObject("user", userService.findByUserName(u.getUsername()));
		return view;
	}*/
	
	@RequestMapping("")
	public ModelAndView list(Long id,@CurrentUser org.springframework.security.core.userdetails.User u) {
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
	
	@RequestMapping("/meetings")
	public ModelAndView onMeetingEdit(Long id, @CurrentUser org.springframework.security.core.userdetails.User u) {
		ModelAndView result;

		if (u.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TEACHER"))) {
			result = new ModelAndView("teacher_meetings");
		} else {
			result = null;
		}
		User user = new User();
		if (id != null) {
			user = userService.findById(id);
		}
		result.addObject("user", user);
		return result;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView saveuser(User user, @CurrentUser org.springframework.security.core.userdetails.User u) {
		userService.save(user);
		ModelAndView view = new ModelAndView("teacher_index");
		view.addObject(user);
		return view;
	}
}
