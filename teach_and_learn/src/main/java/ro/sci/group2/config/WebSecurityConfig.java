package ro.sci.group2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	// @formatter:off
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
			.csrf().disable()
			.headers().disable()
			.authorizeRequests()
				.anyRequest().permitAll()
				.and()
			.formLogin().and()
			.httpBasic();
		}
		// @formatter:on
		// @formatter:off
		/*@Autowired
		public void configGlobal(AuthenticationManagerBuilder auth,
				UserDetailsService userDetailService) throws Exception {
			auth
			.inMemoryAuthentication()
				.withUser("admin")
					.password("password")
					.roles("ADMIN", "USER")
				.and()
				.withUser("user")
					.password("password")
					.roles("USER");
		}*/
		// @formatter:on
}
