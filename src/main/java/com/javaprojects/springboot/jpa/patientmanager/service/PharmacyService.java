package com.javaprojects.springboot.jpa.patientmanager.service;

import java.util.List;

import com.javaprojects.springboot.jpa.patientmanager.model.Pharmacy;



public interface PharmacyService {
	
	public List<Pharmacy> getAllPharmacies();
	
	public void saveOrUpdatePharmacy(Pharmacy thePharmacy);
	
	public Pharmacy getPharmacyById(int pharmacyId);
	
	public void deletePharmacy(int pharmacyId);
	
	public void addPharmacyForPatient(int patientId, String[] pharmacyIds);
	
	

}
