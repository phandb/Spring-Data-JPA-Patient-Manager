package com.javaprojects.springboot.jpa.patientmanager.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaprojects.springboot.jpa.patientmanager.model.Medication;
import com.javaprojects.springboot.jpa.patientmanager.model.Patient;
import com.javaprojects.springboot.jpa.patientmanager.model.Pharmacy;
import com.javaprojects.springboot.jpa.patientmanager.model.Physician;
import com.javaprojects.springboot.jpa.patientmanager.service.MedicationService;
import com.javaprojects.springboot.jpa.patientmanager.service.PatientService;
import com.javaprojects.springboot.jpa.patientmanager.service.PharmacyService;
import com.javaprojects.springboot.jpa.patientmanager.service.PhysicianService;



@Controller
@RequestMapping("/patient")
public class PatientController {
	
	private PatientService patientService;
	private MedicationService medicationService;
	private PharmacyService pharmacyService;
	private PhysicianService physicianService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//Inject constructor
	@Autowired
	public PatientController(PatientService thePatientService ) {
		
		patientService = thePatientService;
		
	}
	
	/**************************************************/
	//Route 
	@GetMapping("/index")
	public String adminIndex() {
		return "admin/index";
	}
	
	//add mapping for /list
	
	@GetMapping("/list")
	public String getAllPatients(Model theModel) {
		
		//get patient from database
		List<Patient> thePatients = patientService.getAllPatients();
		
		//add to the model
		theModel.addAttribute("patientList", thePatients);
		theModel.addAttribute("breadcrumbItem", "List of Patients");
		
		return "/admin/view-all-patients";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		logger.info("Showing form for add");
		
		//create model attribute to bind form data
		Patient thePatient = new Patient();
		String fullName = thePatient.getPatientFullName();
		
		theModel.addAttribute("patient", thePatient);
		theModel.addAttribute("breadcrumbItem", "Add New Patient");
		
		return "/admin/patient-form";
	}
	
	@PostMapping("/savePatient")
	public String savePatient(@ModelAttribute("patient") Patient thePatient) {
		
		if (thePatient.getId() == 0) {
			logger.info("Adding patient: " + thePatient);
		}
		else {
			logger.info("updating patient: " + thePatient);
			
		}
		
		//save the patient using service
		patientService.saveOrUpdatePatient(thePatient);
		
		
		//Use a redirect to prevent duplicate submissions
		return "redirect:/patient/list";
				
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("patientId") int theId, Model theModel) {
		
		logger.info("Show form for update, patientId = " + theId);
		
		//get the patient from service
		Patient thePatient = patientService.getPatientById(theId);
		
		logger.info("The Patient: " + thePatient);
		
		//set patient as a model attribute to pre-populate to the form
		theModel.addAttribute("patient", thePatient);
		theModel.addAttribute("breadcrumbItem", "Patient Name: " + thePatient.getPatientFullName());
		
		//Now send over to the form
		return "/admin/patient-form";
	}
	
	@GetMapping("/delete")
	public String deletePatient(@RequestParam("patientId") int theId) {
		
		logger.info("deleting patient id: " + theId);
		
		//delete the patient
		patientService.deletePatient(theId);
		
		return "redirect:/patient/list";
	}
	
	
	
	/********************* Medication CRUD 	 * @param <CMDBean>**************************************/
	
	@GetMapping("/showPatientInfo")
	public String showPatientInfo(@RequestParam("patientId") int theId, Model theModel) {
		
		logger.info("Patient info, patientId = " + theId);
		Pageable sortedByMedicationName = PageRequest.of(0, 5, Sort.by("medicationName"));
		//get the patient from service
		Patient thePatient = patientService.getPatientById(theId);
		
		//Get pharmacy and physician for patient
		Set<Pharmacy> pharmacyList = thePatient.getPharmacies();
		Set<Physician> physicianList = thePatient.getPhysicians();
		Set<Medication> medicationList = thePatient.getMedications();
		logger.info("The Patient: " + thePatient);
		
		//set patient as a model attribute to pre-populate to the form
		theModel.addAttribute("patientInfo", thePatient);
		theModel.addAttribute("pharmaciesForPatient", pharmacyList);
		theModel.addAttribute("physiciansForPatient", physicianList);
		theModel.addAttribute("medicationsForPatient", medicationList);
		theModel.addAttribute("breadcrumbItem", "Patient Name: " + thePatient.getPatientFullName());
		
			
		
		return "/admin/view-patient-info";
	}
	
	
	
	
	
}
