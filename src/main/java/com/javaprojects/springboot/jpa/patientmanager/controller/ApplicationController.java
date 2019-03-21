package com.javaprojects.springboot.jpa.patientmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.javaprojects.springboot.jpa.patientmanager.service.UserService;

@Controller
public class ApplicationController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String showHome() {
		
		return "/home/index";
	}
	
	@GetMapping("/home/login")
	public String login(Model theModel) {
		return "/home/login";
	}
	
	//Request mapping for user and admin role
	@GetMapping("/admin")
	public String successLogin() {
		
		return "/admin/index";
	}

}
