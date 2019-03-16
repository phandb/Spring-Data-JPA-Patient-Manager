package com.javaprojects.springboot.jpa.patientmanager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javaprojects.springboot.jpa.patientmanager.model.User;
import com.javaprojects.springboot.jpa.patientmanager.service.UserService;
import com.javaprojects.springboot.jpa.patientmanager.user.CustomRegisterUser;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
	
	@Autowired
	private UserService userService;
	
	@InitBinder
	private void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("/showRegistrationForm")
	public String showRegistrationForm(Model theModel) {
		
		theModel.addAttribute("custRegUser", new CustomRegisterUser());
		
		return "/user/registration-form";
	}
	
	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm( @Valid @ModelAttribute("custRegUser") CustomRegisterUser custRegUser,
											BindingResult bindingResult,
											Model theModel) {
		
		String userName = custRegUser.getUserName();
		
		//form validation
		if(bindingResult.hasErrors()) {
			return "/user/registration-form";
		}
		
		//chaeck database for user already exists
		
		User existingUser = userService.findByUsername(userName);
		if(existingUser != null) {
			theModel.addAttribute("custRegUser", new CustomRegisterUser());
			theModel.addAttribute("registrationError", "user name already exists!");
			
			return "/user/registration-form";
		}
		
		//create user account
		userService.saveUser(custRegUser);
		
		return "/admin/index";
	}

}
