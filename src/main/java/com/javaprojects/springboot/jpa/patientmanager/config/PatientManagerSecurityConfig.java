package com.javaprojects.springboot.jpa.patientmanager.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.javaprojects.springboot.jpa.patientmanager.service.UserService;

@Configuration
@EnableWebSecurity
public class PatientManagerSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource securityDataSource;
	
	@Autowired
	private UserService userService;

	@Autowired 
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		//use jdbc authentication
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
		.antMatchers("/").hasRole("EMPLOYEE")
		.antMatchers("/leaders/**").hasRole("MANAGER")
		.antMatchers("/systems/**").hasRole("ADMIN")
		.and()
		.formLogin()
			.loginPage("/showCustomLoginPage")
			.loginProcessingUrl("/authenticateTheUser")
			.successHandler(customAuthenticationSuccessHandler)
			.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/access-denied");
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//authenticationProvider bean definition
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider auth  = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);  //set the custom user details service
		auth.setPasswordEncoder(passwordEncoder());  //set the password encoder-bcrypt
		
		return auth;
	}
	
	


}
