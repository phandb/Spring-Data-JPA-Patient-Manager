package com.javaprojects.springboot.jpa.patientmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@RequestMapping("/patient-manager")
public class LoginController {
	
		
	@GetMapping("/login-page")
	public String loginpage() {
		
		return "/home/login";
	}
	
	@GetMapping("/access-denied")
	public String showAccessDenied() {
		
		return "/errors/access-denied";
	}

}
