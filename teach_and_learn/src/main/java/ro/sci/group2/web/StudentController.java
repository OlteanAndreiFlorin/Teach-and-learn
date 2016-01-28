package ro.sci.group2.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		ModelAndView view = new ModelAndView("student_list");
		view.addObject("students",studentService.listAll());
		return view;
	}
	@RequestMapping(value="/sorted" , method= RequestMethod.POST)
	public ModelAndView list(String order){
		ModelAndView view = list();
		view.addObject("students",studentService.listAll(order));
		return view;
	}
	@RequestMapping(value="/search",method= RequestMethod.POST)
	public ModelAndView listByName(String query){
		ModelAndView view= list();
		view.addObject("students",studentService.findByName(query));
		return view;
	}
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView saveStudent(Student student){
		studentService.save(student);
		return list();
	}
	
	@RequestMapping("/student_edit")
	public ModelAndView onEdit(Long id){
		ModelAndView result = new ModelAndView("student_edit");
		Student student = new Student();
		if(id != null){
			student = studentService.findById(id);
		}
		result.addObject("student" , student);
		return result;
	}
	
	@RequestMapping("/student_delete")
	public ModelAndView onDelete(long id){
		if(!studentService.delete(id)){
			throw new IllegalStateException("Non existing student");
		}
		else
		{
			return list();
		}

	}

}
