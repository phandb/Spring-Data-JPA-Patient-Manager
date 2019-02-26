package com.javaprojects.springboot.jpa.patientmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javaprojects.springboot.jpa.patientmanager.model.Patient;
import com.javaprojects.springboot.jpa.patientmanager.model.Pharmacy;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Integer> {
	
	List<Pharmacy> findAllByPatients(Patient patient);

}
