package ro.sci.group2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ro.sci.group2.dao.CourseDAO;
import ro.sci.group2.dao.UserDAO;
import ro.sci.group2.dao.db.JDBCCourseDAO;
import ro.sci.group2.dao.db.JDBCUserDAO;
import ro.sci.group2.dao.inmemory.IMCourseDAO;
import ro.sci.group2.dao.inmemory.IMUserDAO;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class TeachAndLearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeachAndLearnApplication.class, args);
	}

	@Bean
	public UserDAO userDao() {
		return // new IMUserDAO();
		new JDBCUserDAO("ec2-54-83-12-22.compute-1.amazonaws.com", "5432", "d1vssoh84qkbg3", "vacmpcjhlpcnft",
				"6ZAEauN0X589o05fxrypEIl2v_");

	}

	@Bean
	public CourseDAO courseDao() {
		return // new IMCourseDAO();
		new JDBCCourseDAO("ec2-54-225-165-132.compute-1.amazonaws.com", "5432", "dfqrqj6ph57snt", "ycrkbiarkashvd",
				"rOPqWXR3ddgI7Xry7u9HGLiIK3");
	}
}
