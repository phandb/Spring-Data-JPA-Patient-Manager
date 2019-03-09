package com.javaprojects.springboot.jpa.patientmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {
	
	@GetMapping("/")
	public String showHome() {
		
		return "home";
	}
	
	//Request mapping for user and admin role
	@GetMapping("/admin")
	public String showAdmins() {
		
		return "admin";
	}

}
