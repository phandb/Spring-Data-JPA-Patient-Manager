package com.javaprojects.springboot.jpa.patientmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.javaprojects.springboot.jpa.patientmanager.service.UserService;

@Configuration
@EnableWebSecurity
public class PatientManagerSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService userService;
	
	
	@Autowired 
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

	

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/user/**",
							"/home/**", 
							"/error/**",
							"/fragments/**",
							"/registration/**")
					.permitAll()  /*all users are allowed to enter application*/
				/* Specify access for each role*/
				.antMatchers("/admin/index").hasRole("GUEST")
				.antMatchers("/admin/**").hasRole("USER")
				.antMatchers("/admin/**").hasRole("ADMIN")
				/*Any other request will be authenticated*/
				.anyRequest().authenticated()
			.and()
				/*start form login*/
				.formLogin() 
					.loginPage("/showLoginPage")
					.loginProcessingUrl("/user/login")
					.successHandler(customAuthenticationSuccessHandler)
					.defaultSuccessUrl("/admin/index")
					/* parameters in the submitted login form*/
					.usernameParameter("username")
					.passwordParameter("password")
					.permitAll()
			.and()
				/* call logout form*/
				.logout()
				.logoutUrl("/user/logout")
				.invalidateHttpSession(true).deleteCookies("JSESSIONID")
				.permitAll()
			.and()
				.exceptionHandling().accessDeniedPage("/error/access-denied")
			/* disable Cross-Site Request Forgery*/
			.and().csrf().disable();
	
	}
	

	
	@Override
	public void configure(WebSecurity webSecurity) throws Exception{
		webSecurity.ignoring()
				   .antMatchers("/resources/**", "/static/**", "/vendor/**", "/js/**", "/css/**");
	}
	
	
	//beans
	//bcrypt bean definition
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//authenticationProvider bean definition
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService); //set the custom user details service
		auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
		return auth;
	}
	


}
