package com.javaprojects.springboot.jpa.patientmanager.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaprojects.springboot.jpa.patientmanager.exception.ResourceNotFoundException;
import com.javaprojects.springboot.jpa.patientmanager.model.Patient;
import com.javaprojects.springboot.jpa.patientmanager.model.Pharmacy;
import com.javaprojects.springboot.jpa.patientmanager.model.Physician;
import com.javaprojects.springboot.jpa.patientmanager.repository.PatientRepository;
import com.javaprojects.springboot.jpa.patientmanager.repository.PharmacyRepository;


@Service
public class PharmacyServiceImpl implements PharmacyService {
	
	private PatientRepository patientRepository;
	private PatientServiceImpl patientServiceImpl;
	private PharmacyRepository pharmacyRepository;
	
	private Logger pharmacyLogger = LoggerFactory.getLogger(getClass());
	
	//Inject Constructor
	@Autowired
	public PharmacyServiceImpl(PharmacyRepository pharmacyRepository,
							   PatientRepository patientRepository) {
		
		this.pharmacyRepository = pharmacyRepository;
		this.patientRepository = patientRepository;
	}
		

	@Override
	public List<Pharmacy> getAllPharmacies() {
		
		return pharmacyRepository.findAll();
	}

	@Override
	public void saveOrUpdatePharmacy(Pharmacy thePharmacy) {

		pharmacyRepository.save(thePharmacy);

	}

	@Override
	public Pharmacy getPharmacyById(int pharmacyId) {

		Optional<Pharmacy> result = pharmacyRepository.findById(pharmacyId);
		
		Pharmacy thePharmacy = null;
		
		if(result.isPresent()){
			thePharmacy = result.get();
			
		}else {
			throw new ResourceNotFoundException("Could not find the pharmacy id: " + pharmacyId);
		}
			return thePharmacy;
	
	}

	@Override
	public void deletePharmacy(int pharmacyId) {
		pharmacyRepository.deleteById(pharmacyId);
	}


	@Override
	public void addPharmacyForPatient(int patientId, String[] pharmacyIds) {

		Patient thePatient = patientRepository.getOne(patientId);
		//retrieve the check box list
		if (pharmacyIds != null) {
			for (String strPharmacyId : pharmacyIds) {
				try {
					int pharmacyId = Integer.parseInt(strPharmacyId);
				
					Pharmacy pharmacy = getPharmacyById(pharmacyId);
					thePatient.addPharmacy(pharmacy);
					patientRepository.save(thePatient);
					
				}catch(NumberFormatException e) {
					System.out.println("not a number");
				}
			}
		}
		
	}



}
