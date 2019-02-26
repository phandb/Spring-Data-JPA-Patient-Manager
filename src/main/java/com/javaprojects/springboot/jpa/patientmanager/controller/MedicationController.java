package com.javaprojects.springboot.jpa.patientmanager.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaprojects.springboot.jpa.patientmanager.model.Medication;
import com.javaprojects.springboot.jpa.patientmanager.model.Patient;
import com.javaprojects.springboot.jpa.patientmanager.service.MedicationService;
import com.javaprojects.springboot.jpa.patientmanager.service.PatientService;

@Controller
@RequestMapping("/medication")
public class MedicationController {
	
	private PatientService patientService;
	private MedicationService medicationService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public MedicationController(MedicationService theMedicationService,
								PatientService thePatientService) {
		medicationService = theMedicationService;
		patientService = thePatientService;
	}
	
	@GetMapping("/{medicationId}/medications")
	public String getAllMedicationsByPatientId(@PathVariable (value="patientId") int patientId,
														Pageable pageable){
		return "/admin/view-patient-info";
		
	}
	
	@GetMapping("/showAddMedicationForm")
	public String showFormForAdd(@RequestParam("patientId") int patientId, Model theModel) {
		
		Medication theMedication = new Medication();
		Patient thePatient = patientService.getPatientById(patientId);
		
		theModel.addAttribute("medication", theMedication);
		theModel.addAttribute("patient", thePatient);
		theModel.addAttribute("breadcrumItem", "Add Medication For Patient");
		
		
		return "/admin/medication-form";
		
	}
	
	@GetMapping("/showUpdateMedicationForm")
	public String showFormForUpdate(@RequestParam("patientId") int patientId,
									@RequestParam("medicationId") int medicationId,
									Model theModel) {
		
		Medication theMedication = medicationService.getMedicationById(medicationId);
		Patient thePatient = patientService.getPatientById(patientId);
		
		theModel.addAttribute("medication",  theMedication);
		theModel.addAttribute("patient", thePatient);
		theModel.addAttribute("breadcrumItem", "Update Medication");
		
		return "/admin/medication-form";
	}
	
	@PostMapping("/saveMedication")
	public String saveMedication(@ModelAttribute("medication") Medication theMedication,
								@RequestParam("patientId") int patientId,
								@RequestParam("medicationId") int medicationId) {
		
		if( medicationId == 0 ) {
			patientService.saveMedicationForPatient(patientId, theMedication);
		} else{
			medicationService.updateMedicationForPatient(patientId, theMedication);
		}
	
				
		return "redirect:/patient/showPatientInfo?patientId=" + patientId;
	}
	
	
	@GetMapping("/deleteMedication")
	public String deleteMedication(@RequestParam("medicationId") int medicationId,
									@RequestParam("patientId") int patientId){
		
		//logger.info("deleting medication id: " + medicationId);
		Medication theMedication = medicationService.getMedicationById(medicationId);
		
		patientService.removeMedicationFromPatient(patientId, theMedication);
		
		
		return "redirect:/patient/showPatientInfo?patientId=" + patientId;
	}

}
