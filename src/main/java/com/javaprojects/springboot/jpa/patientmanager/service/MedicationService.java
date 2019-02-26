package com.javaprojects.springboot.jpa.patientmanager.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.javaprojects.springboot.jpa.patientmanager.model.Medication;
import com.javaprojects.springboot.jpa.patientmanager.model.Patient;



public interface MedicationService {
	
	public List<Medication> getMedicationsForPatient(Patient patient, Pageable pageable);
	
	public void updateMedicationForPatient(int patientId, Medication theMedication);
	
	
	public Medication getMedicationById(int medicationId);
	
	
}
