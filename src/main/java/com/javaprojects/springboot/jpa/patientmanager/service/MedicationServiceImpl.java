package com.javaprojects.springboot.jpa.patientmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.javaprojects.springboot.jpa.patientmanager.model.Medication;
import com.javaprojects.springboot.jpa.patientmanager.model.Patient;
import com.javaprojects.springboot.jpa.patientmanager.repository.MedicationRepository;
import com.javaprojects.springboot.jpa.patientmanager.repository.PatientRepository;

@Service
public class MedicationServiceImpl implements MedicationService {
	
	private PatientRepository patientRepository;
	private MedicationRepository medicationRepository;
	private PatientServiceImpl patientServiceImpl;
	
	
	@Autowired
	public MedicationServiceImpl(MedicationRepository theMedicationRepository) {
		
		medicationRepository = theMedicationRepository;
	}
	

	@Override
	public List<Medication> getMedicationsForPatient(Patient patient, Pageable pageable) {
		
		
		return medicationRepository.findByPatient(patient);
	}
	
	@Override
	public void updateMedicationForPatient(int patientId, Medication theMedication) {
		
		
		Medication tempMedication =  medicationRepository.findByIdAndPatientId(theMedication.getId(), patientId);
		
		tempMedication.setMedicationName(theMedication.getMedicationName());
		tempMedication.setMedicationStrength(theMedication.getMedicationStrength());
		tempMedication.setMedicationDosage(theMedication.getMedicationDosage());
		
		//save medication
		medicationRepository.saveAndFlush(tempMedication);
	
	}

	

	@Override
	public Medication getMedicationById(int medicationId) {
		
		Medication tempMedication = medicationRepository.getOne(medicationId);
		
		return tempMedication;
	}


}
