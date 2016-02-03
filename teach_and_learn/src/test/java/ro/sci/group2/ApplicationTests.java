package ro.sci.group2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ro.sci.group2.dao.UserDAO;
//import ro.sci.group2.dao.db.JDBCUserDAO;
import ro.sci.group2.dao.inmemory.IMUserDAO;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ApplicationTests {
 public static void main(String[] args) {
  SpringApplication.run(ApplicationTests.class, args);
  
 }
 
 @Bean
 public UserDAO employeeDao() {
  return  new IMUserDAO();
    //new JDBCUserDAO("localhost", "5432", "test", "test", "test");
 }

}
