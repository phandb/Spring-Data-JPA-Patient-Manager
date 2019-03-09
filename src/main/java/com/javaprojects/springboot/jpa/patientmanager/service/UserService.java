package com.javaprojects.springboot.jpa.patientmanager.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.javaprojects.springboot.jpa.patientmanager.model.User;
import com.javaprojects.springboot.jpa.patientmanager.user.CustomRegisterUser;

public interface UserService extends UserDetailsService{

	User findByUsername(String userName);
	
	public void saveUser(CustomRegisterUser custRegUser);
	
//void save(CustomRegisterUser customRegisterUser);

	//UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException;

}
