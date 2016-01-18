package ro.sci.group2.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ro.sci.group2.domain.Student;
import ro.sci.group2.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
	@Autowired
	StudentService studentService;

	@RequestMapping("")
	public ModelAndView list() {
		Student student=new Student();
		student.setFirstName("Test");
		student.setLastName("Student");
		studentService.save(student);
		ModelAndView view = new ModelAndView("student_list");
		view.addObject("students",studentService.listAll());
		return view;
	}

}
