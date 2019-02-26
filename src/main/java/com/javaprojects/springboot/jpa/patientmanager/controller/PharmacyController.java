package com.javaprojects.springboot.jpa.patientmanager.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaprojects.springboot.jpa.patientmanager.model.Patient;
import com.javaprojects.springboot.jpa.patientmanager.model.Pharmacy;
import com.javaprojects.springboot.jpa.patientmanager.service.PatientService;
import com.javaprojects.springboot.jpa.patientmanager.service.PharmacyService;

@Controller
@RequestMapping("/pharmacy")
public class PharmacyController {
	
	private PatientService patientService;
	private PharmacyService pharmacyService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	public PharmacyController(PharmacyService thePharmacyService, 
							   PatientService thePatientService) {
		
		pharmacyService = thePharmacyService;
		patientService = thePatientService;
	}
	
	@GetMapping("/list")
	public String getAllPharmacies(Model theModel) {
		
		List<Pharmacy> thePharmacies = pharmacyService.getAllPharmacies();
		
		theModel.addAttribute("pharmacyList", thePharmacies);
		theModel.addAttribute("breadcrumbItem", "List of Pharmacies");
		
		return "/admin/view-all-pharmacies";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		logger.info("Showing form for add");
		
		//create model attribute to bind form data
		Pharmacy thePharmacy = new Pharmacy();
		
		theModel.addAttribute("pharmacy", thePharmacy);
		theModel.addAttribute("breadcrumbItem", "Add New Pharmacy");
		
		return "/admin/pharmacy-form";
	}
	
	@PostMapping("/savePharmacy")
	public String savePharmacy(@ModelAttribute("pharmacy") Pharmacy thePharmacy) {
		
		if (thePharmacy.getId() == 0) {
			logger.info("Adding pharmacy: " + thePharmacy);
		}
		else {
			logger.info("updating pharmacy: " + thePharmacy);
			
		}
		
		//save the patient using service
		pharmacyService.saveOrUpdatePharmacy(thePharmacy);
		
		return "redirect:/pharmacy/list";
				
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("pharmacyId") int theId, Model theModel) {
		
		logger.info("Show form for update, pharmacyId = " + theId);
		
		//get the patient from service
		Pharmacy thePharmacy = pharmacyService.getPharmacyById(theId);
		
		logger.info("The Pharmacy: " + thePharmacy);
		
		//set patient as a model attribute to pre-populate to the form
		theModel.addAttribute("pharmacy", thePharmacy);
		theModel.addAttribute("breadcrumbItem", "Update Pharmacy");
		
		//Now send over to the form
		return "/admin/pharmacy-form";
	}
	
	@GetMapping("/delete")
	public String deletePharmacy(@RequestParam("pharmacyId") int theId) {
		
		logger.info("deleting pharmacy: " + theId);
		
		//delete the patient
		pharmacyService.deletePharmacy(theId);
		
		return "redirect:/pharmacy/list";
	}
	
	@GetMapping("/addPharmacyToPatientForm")
	public String addPharmacyToPatientForm(@RequestParam("patientId") int patientId, Model theModel ) {
		
		List<Pharmacy> thePharmacies = pharmacyService.getAllPharmacies();
		
		Patient thePatient = patientService.getPatientById(patientId);
		
		theModel.addAttribute("pharmacyList", thePharmacies);
		theModel.addAttribute("patient", thePatient);
		theModel.addAttribute("breadcrumbItem", "List of Pharmacies");
		
		return "/admin/add-pharmacies-to-patient";
	}
	

	@PostMapping("/addPharmacyToPatient")
	public String addPharmacyToPatient(@RequestParam("thePatientId") int patientId,
										@RequestParam("pharmacyCheckboxList") String[] pharmacyIdList)  {
		
		//Patient thePatient = patientService.getPatientById(patientId);
		pharmacyService.addPharmacyForPatient(patientId, pharmacyIdList);	
		
		return "redirect:/patient/showPatientInfo?patientId=" + patientId ;
		
	}
	
	@GetMapping("/deletePharmacy")
	public String deletePharmacy(@RequestParam("pharmacyId") int pharmacyId,
								 @RequestParam("patientId") int patientId) {
		
		Pharmacy thePharmacy = pharmacyService.getPharmacyById(pharmacyId);
		
		patientService.removePharmacyFromPatient(patientId, thePharmacy);
		
		return "redirect:/patient/showPatientInfo?patientId=" + patientId ;
		
		
		
	}
	
	

}
