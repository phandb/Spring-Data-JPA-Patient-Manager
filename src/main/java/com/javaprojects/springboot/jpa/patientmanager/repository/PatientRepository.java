package com.javaprojects.springboot.jpa.patientmanager.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javaprojects.springboot.jpa.patientmanager.model.Medication;
import com.javaprojects.springboot.jpa.patientmanager.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
	
	//that is it...no need to write any code
	
	//add a method to sort by last name
	public List<Patient> findAllByOrderByLastNameAsc();
	//public Patient findById();

	//public Set<Medication> findAllByPatientId(int patientId);
	
	

}
