package com.javaprojects.springboot.jpa.patientmanager.service;

import java.util.List;
import java.util.Set;

import com.javaprojects.springboot.jpa.patientmanager.model.Medication;
import com.javaprojects.springboot.jpa.patientmanager.model.Patient;
import com.javaprojects.springboot.jpa.patientmanager.model.Pharmacy;
import com.javaprojects.springboot.jpa.patientmanager.model.Physician;



public interface PatientService {
	
	public List<Patient> getAllPatients();
	
	public void saveOrUpdatePatient(Patient thePatient);
	
	public Patient getPatientById(int patientId);
	
	public void deletePatient(int patientId);
	
	public void saveMedicationForPatient(int patientId, Medication theMedication);
	
	public void removeMedicationFromPatient(int patientId, Medication theMedication);
	
	public List<Pharmacy> getPharmacyForPatient(int patientId);
	
	public List<Physician> getPhysicianForPatient(int patientId);
	
	public void removePhysicianFromPatient(int patientId, Physician thePhysician);
	
	public void removePharmacyFromPatient(int patientId, Pharmacy thePharmacy);

}
