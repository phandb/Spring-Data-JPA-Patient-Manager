package com.javaprojects.springboot.jpa.patientmanager.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.javaprojects.springboot.jpa.patientmanager.model.User;
import com.javaprojects.springboot.jpa.patientmanager.user.CustomRegisterUser;

public interface UserService {

	User findByUserName(String userName);
	
	void save(CustomRegisterUser customRegisterUser);

	//UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException;

}
