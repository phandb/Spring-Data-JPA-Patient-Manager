package com.javaprojects.springboot.jpa.patientmanager.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaprojects.springboot.jpa.patientmanager.exception.ResourceNotFoundException;
import com.javaprojects.springboot.jpa.patientmanager.model.Medication;
import com.javaprojects.springboot.jpa.patientmanager.model.Patient;
import com.javaprojects.springboot.jpa.patientmanager.model.Pharmacy;
import com.javaprojects.springboot.jpa.patientmanager.model.Physician;
import com.javaprojects.springboot.jpa.patientmanager.repository.MedicationRepository;
import com.javaprojects.springboot.jpa.patientmanager.repository.PatientRepository;
import com.javaprojects.springboot.jpa.patientmanager.repository.PharmacyRepository;
import com.javaprojects.springboot.jpa.patientmanager.repository.PhysicianRepository;

@Service
public class PatientServiceImpl implements PatientService {

	private PatientRepository patientRepository;
	private MedicationRepository medicationRepository;
	private PhysicianRepository physicianRepository;
	private PharmacyRepository pharmacyRepository;
	
	
	
	//Inject Constructor
	@Autowired
	public PatientServiceImpl(PatientRepository patientRepository,
							 MedicationRepository medicationRepository) {
		this.patientRepository = patientRepository;
		this.medicationRepository = medicationRepository;
	}
	
	
	@Override
	public List<Patient> getAllPatients() {

		
		return patientRepository.findAllByOrderByLastNameAsc();
		
	}

	@Override
	public void saveOrUpdatePatient(Patient thePatient) {

		patientRepository.save(thePatient);

	}

	@Override
	public Patient getPatientById(int patientId) {
		Optional<Patient> result = patientRepository.findById(patientId);
		
		Patient thePatient = null;
		
		if(result.isPresent()) {
			thePatient = result.get();
		}
		else {
			//could not find the patient
			throw new ResourceNotFoundException("Did not find patient id - " + patientId);
		}
		
		return thePatient;
	}

	@Override
	public void deletePatient(int patientId) {
		// make REST call
		patientRepository.deleteById(patientId);;

	}
/********************************************************/
	@Override
	public void saveMedicationForPatient(int patientId, Medication theMedication) {
		
		Patient thePatient = getPatientById(patientId);
					
		thePatient.addMedication(theMedication);
		
		patientRepository.saveAndFlush(thePatient);
	
	}
	
	@Override
	public void removeMedicationFromPatient(int patientId, Medication theMedication) {
		
		Patient thePatient = getPatientById(patientId);
		
		thePatient.removeMedication(theMedication);
		
		patientRepository.save(thePatient);
		
		
		
	}
	
	@Override
	public void removePharmacyFromPatient(int patientId, Pharmacy thePharmacy) {
		
		Patient thePatient = getPatientById(patientId);
		
		thePatient.removePharmacy(thePharmacy);
		
		patientRepository.save(thePatient);
	
	}
	
	@Override
	public void removePhysicianFromPatient(int patientId, Physician thePhysician) {
		
		Patient thePatient = getPatientById(patientId);
		
		thePatient.removePhysician(thePhysician);
		
		patientRepository.save(thePatient);
		
		
		
	}
	
	

	@Override
	public List<Pharmacy> getPharmacyForPatient(int patientId) {

		List<Pharmacy> pharmacyForPatient = new ArrayList<Pharmacy>();
		
		return pharmacyForPatient;
		
	}

	@Override
	public List<Physician> getPhysicianForPatient(int patientId) {

		List<Physician> physicianForPatient = new ArrayList<Physician>();
		
		return physicianForPatient;
		
	}

	
}
