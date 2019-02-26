package com.javaprojects.springboot.jpa.patientmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javaprojects.springboot.jpa.patientmanager.model.Medication;
import com.javaprojects.springboot.jpa.patientmanager.model.Patient;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Integer> {
	
	@Query("select m from Medication m where m.patient.id = :patientId")
	List<Medication> findByPatientId(@Param("patientId") int patientId);
	
	List<Medication> findByPatient(Patient patient);
	
	Medication findByIdAndPatientId(int medicationId, int PatientId );

	

}
