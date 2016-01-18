package ro.sci.group2.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.sci.group2.dao.StudentDao;
import ro.sci.group2.domain.Student;

@Service
public class StudentService {
	@Autowired
	private StudentDao dao;

	public Student save(Student student) {
		return dao.update(student);
	}

	public Collection<Student> listAll() {
		return dao.getAll();
	}

}
