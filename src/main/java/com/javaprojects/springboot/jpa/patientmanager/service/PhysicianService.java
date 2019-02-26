package com.javaprojects.springboot.jpa.patientmanager.service;

import java.util.List;

import com.javaprojects.springboot.jpa.patientmanager.model.Patient;
import com.javaprojects.springboot.jpa.patientmanager.model.Physician;



public interface PhysicianService {

	public List<Physician> getAllPhysicians();
	
	public void saveOrUpdatePhysician(Physician thePhysician);
	
	public Physician getPhysicianById(int physicianId);
	
	public void deletePhysician(int physicianId);
	
	public void addPhysicianForPatient(int patientId, String[] physicianIds);
	
	
	
	
}
