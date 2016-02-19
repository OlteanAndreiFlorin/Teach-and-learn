package ro.sci.group2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ro.sci.group2.dao.CourseDAO;
import ro.sci.group2.dao.MeetingDAO;
import ro.sci.group2.dao.UserDAO;
import ro.sci.group2.dao.db.JDBCCourseDAO;
import ro.sci.group2.dao.db.JDBCMeetingDAO;
import ro.sci.group2.dao.db.JDBCUserDAO;
import ro.sci.group2.dao.inmemory.IMCourseDAO;
import ro.sci.group2.dao.inmemory.IMMeetingDAO;
import ro.sci.group2.dao.inmemory.IMUserDAO;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//@formatter:off
	@Bean
	public UserDAO userDao() {
		return  //new IMUserDAO();
		new JDBCUserDAO("localhost", "5432", "test", "test", "test");
		
		/* new JDBCUserDAO("ec2-54-83-12-22.compute-1.amazonaws.com", "5432",
		 		"d1vssoh84qkbg3", "vacmpcjhlpcnft",
		 		"6ZAEauN0X589o05fxrypEIl2v_");*/
	}
//@formatter:on

//@formatter:off
	@Bean
	public CourseDAO courseDao() {
		return  //new IMCourseDAO();
		new JDBCCourseDAO("localhost", "5432", "test", "test", "test");
		
		/* new JDBCCourseDAO("ec2-54-83-12-22.compute-1.amazonaws.com", "5432",
		 		"d1vssoh84qkbg3", "vacmpcjhlpcnft",
		 		"6ZAEauN0X589o05fxrypEIl2v_");*/
	}
//@formatter:on

//@formatter:off
	@Bean
	public MeetingDAO meetingDao(){
		return //new IMMeetingDAO();
		new JDBCMeetingDAO("localhost", "5432", "test", "test", "test");
		
		/*new JDBCMeetingDAO("ec2-54-83-12-22.compute-1.amazonaws.com", "5432",
				"d1vssoh84qkbg3", "vacmpcjhlpcnft",
				"6ZAEauN0X589o05fxrypEIl2v_");*/
	}
//@formatter:on
}
