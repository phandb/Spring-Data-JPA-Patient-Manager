package com.javaprojects.springboot.jpa.patientmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.javaprojects.springboot.jpa.patientmanager.model.User;
import com.javaprojects.springboot.jpa.patientmanager.service.UserService;

@Controller
public class ApplicationController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String showHome() {
		
		return "/admin/index";
	}
	
	@GetMapping("/login")
	public String login(Model theModel) {
		return "login";
	}
	
	//Request mapping for user and admin role
	@GetMapping("/admin/index")
	public String showAdmins() {
		/*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user = userService.findByUsername(auth.getName());*/
		return "/admin/index";
	}

}
