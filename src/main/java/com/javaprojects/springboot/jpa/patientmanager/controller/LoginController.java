package com.javaprojects.springboot.jpa.patientmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@RequestMapping("/patient-manager")
public class LoginController {
	
		
	@GetMapping("/showLoginPage")
	public String login() {
		
		return "/user/login";
	}
	
	@GetMapping("/access-denied")
	public String showAccessDenied() {
		
		return "/errors/access-denied";
	}

}
