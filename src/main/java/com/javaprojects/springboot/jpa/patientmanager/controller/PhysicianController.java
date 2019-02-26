package com.javaprojects.springboot.jpa.patientmanager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaprojects.springboot.jpa.patientmanager.model.Patient;
import com.javaprojects.springboot.jpa.patientmanager.model.Physician;
import com.javaprojects.springboot.jpa.patientmanager.service.PatientService;
import com.javaprojects.springboot.jpa.patientmanager.service.PhysicianService;

@Controller
@RequestMapping("/physician")
public class PhysicianController {
	
	private PatientService patientService;
	private PhysicianService physicianService;
	
	@Autowired
	public PhysicianController(PhysicianService thePhysicianService, 
							   PatientService thePatientService) {
		
		physicianService = thePhysicianService;
		patientService = thePatientService;
	}
	
	@GetMapping("/list")
	public String getAllPhysicians(Model theModel) {
		
		List<Physician> thePhysicians = physicianService.getAllPhysicians();
		
		theModel.addAttribute("physicianList", thePhysicians);
		theModel.addAttribute("breadcrumbItem", "List of Physicians");
		
		return "/admin/view-all-physicians";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		
		//create model attribute to bind form data
		Physician thePhysician = new Physician();
		
		theModel.addAttribute("physician", thePhysician);
		theModel.addAttribute("breadcrumbItem", "Add Physician");
		
		return "/admin/physician-form";
	}
	
	@PostMapping("/savePhysician")
	public String savePhysician(@ModelAttribute("physician") Physician thePhysician) {
				
		//save the patient using service
		physicianService.saveOrUpdatePhysician(thePhysician);
		
		return "redirect:/physician/list";
				
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("physicianId") int theId, Model theModel) {
		
				
		//get the patient from service
		Physician thePhysician = physicianService.getPhysicianById(theId);
		
			
		//set patient as a model attribute to pre-populate to the form
		theModel.addAttribute("physician", thePhysician);
		theModel.addAttribute("breadcrumbItem", "Update Physician");
		
		//Now send over to the form
		return "/admin/physician-form";
	}
	
	@GetMapping("/delete")
	public String deletePhysician(@RequestParam("physicianId") int theId) {
		
	
		
		//delete the patient
		physicianService.deletePhysician(theId);
		
		return "redirect:/physician/list";
	}
	
	@GetMapping("/addPhysicianToPatientForm")
	public String addPhysicianToPatientForm(@RequestParam("patientId") int patientId, Model theModel) {
		
		
		
		//get physicians from service
		List<Physician> thePhysicians = physicianService.getAllPhysicians();
		Patient thePatient = patientService.getPatientById(patientId);
		//logger.info("thePhysicians: " + thePhysicians);
		
		//add the physicians to model
		theModel.addAttribute("physicianList", thePhysicians);
		theModel.addAttribute("patient", thePatient);
		theModel.addAttribute("breadcrumbItem", "List of Physicians");
		
	
		
		return "/admin/add-physicians-to-patient";
		
	}
	
	
	@PostMapping("/addPhysicianToPatient")
	public String addPhysicianToPatient(@RequestParam("thePatientId") int patientId,
										@RequestParam("physicianCheckboxList") String[] physicianIdList)  {
		
		//Patient thePatient = patientService.getPatientById(patientId);
		physicianService.addPhysicianForPatient(patientId, physicianIdList);	
		
		return "redirect:/patient/showPatientInfo?patientId=" + patientId ;
		
	}
	
	@GetMapping("/deletePhysician")
	public String deletePhysician(@RequestParam("physicianId") int physicianId,
								 @RequestParam("patientId") int patientId) {
		
		Physician thePhysician = physicianService.getPhysicianById(physicianId);
		
		patientService.removePhysicianFromPatient(patientId, thePhysician);
		
		return "redirect:/patient/showPatientInfo?patientId=" + patientId;
		
	}

}
