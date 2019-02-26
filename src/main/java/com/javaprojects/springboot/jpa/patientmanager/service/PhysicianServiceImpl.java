package com.javaprojects.springboot.jpa.patientmanager.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaprojects.springboot.jpa.patientmanager.exception.ResourceNotFoundException;
import com.javaprojects.springboot.jpa.patientmanager.model.Patient;
import com.javaprojects.springboot.jpa.patientmanager.model.Physician;
import com.javaprojects.springboot.jpa.patientmanager.repository.PatientRepository;
import com.javaprojects.springboot.jpa.patientmanager.repository.PhysicianRepository;


@Service
public class PhysicianServiceImpl implements PhysicianService {

	private PatientRepository patientRepository;
	private PhysicianRepository physicianRepository;
	//private PatientServiceImpl patientServiceImpl;
	

	
	private Logger physicianLogger = LoggerFactory.getLogger(getClass());
	
	//Inject Constructor
	@Autowired
	public PhysicianServiceImpl(PhysicianRepository thePhysicianRepository,
								PatientRepository thePatientRepository) {
		physicianRepository = thePhysicianRepository;
		patientRepository = thePatientRepository;
	}

	@Override
	public List<Physician> getAllPhysicians() {

	
		return physicianRepository.findAll();
	}

	@Override
	public void saveOrUpdatePhysician(Physician thePhysician) {

		physicianRepository.save(thePhysician);
	}

	@Override
	public Physician getPhysicianById(int physicianId) {

		Optional<Physician> result = physicianRepository.findById(physicianId);
		
		Physician thePhysician = null;
		
		if(result.isPresent()) {
			thePhysician = result.get();
		}else {
			throw new ResourceNotFoundException("Could not find physician with id: " + physicianId);
		}
		
		return thePhysician;
	
	}

	@Override
	public void deletePhysician(int physicianId) {

		physicianRepository.deleteById(physicianId);
	}
	
	@Override
	public void addPhysicianForPatient(int patientId, String[] physicianIds) {
		
		Patient thePatient = patientRepository.getOne(patientId);
		//retrieve the check box list
		if (physicianIds != null) {
			for (String strPhysicianId : physicianIds) {
				try {
					int physicianId = Integer.parseInt(strPhysicianId);
				
					Physician physician = getPhysicianById(physicianId);
					thePatient.addPhysician(physician);
					patientRepository.save(thePatient);
					
				}catch(NumberFormatException e) {
					System.out.println("not a number");
				}
			}
		}
	}
	/*	
	@Override
	public void addPhysicianForPatient(int patientId, int physicianId) {
		
		Patient thePatient = patientServiceImpl.getPatientById(patientId);
		
		if (thePatient == null) {
			
		}
		//retrieve the check box list
		
		Physician physician = getPhysicianById(physicianId);
		
		thePatient.addPhysician(physician);
		
		patientRepository.save(thePatient);
			
	}
	*/

	


}

