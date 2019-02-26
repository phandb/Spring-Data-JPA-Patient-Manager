package com.javaprojects.springboot.jpa.patientmanager.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="medications")
public class Medication implements Serializable {
	//annotate the class as an entity and map to database table

		//define the fields
		//annotate the fields with db column names
		/***
		 * Set up relationship with patients table using FK patient_id
		 */
		//create constructors
		//generate getter/setter methods
		//generate toString() method
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="medication_name")
	private String medicationName;
	
	@Column(name="medication_strength")
	private String medicationStrength;
	
	@Column(name="medication_dosage")
	private String medicationDosage;
	
	/************************Setup Relationship with Patient******************************/
	//Setup many to one relationship with patient table
	//one patient can have many medications
	
	/*@ManyToOne (cascade = { CascadeType.PERSIST,
							CascadeType.MERGE,
							CascadeType.DETACH,
							CascadeType.REFRESH 
							})*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="patient_id")
	//@OnDelete(action = OnDeleteAction.CASCADE)
	
	//This prevent recursive relationship in JSON which lead to an infinite loop of nested objects in response
	//@JsonIgnore
	private Patient patient;
	
	public Patient getPatient() {
		return this.patient;
	}


	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public void updateMedication(Medication medication) {
		
		//medication.add(patient);
	}
	
	
	//it’s good practice to override equals and hashCode 
	//for the child entity in a bidirectional association.
	@Override
    public boolean equals(Object Obj) {
        if (this == Obj) return true;
        if (!(Obj instanceof Medication )) return false;
        return id != 0 && id==(((Medication) Obj).id);
    }
    @Override
    public int hashCode() {
        return 31;
    }
	

	/*********************************************************************************/
	
	//Constructors
	public Medication() {
		
	}
	
	/**
	 * @param medicationName
	 * @param medicationStrength
	 * @param medicationDosage
	 */
	public Medication(String medicationName, String medicationStrength, String medicationDosage ) {
		this.medicationName = medicationName;
		this.medicationStrength = medicationStrength;
		this.medicationDosage = medicationDosage;
	}
	

	public int getId() {
		return this.id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getMedicationName() {
		return medicationName;
	}


	public void setMedicationName(String medicationName) {
		this.medicationName = medicationName;
	}


	public String getMedicationStrength() {
		return medicationStrength;
	}


	public void setMedicationStrength(String medicationStrength) {
		this.medicationStrength = medicationStrength;
	}


	public String getMedicationDosage() {
		return medicationDosage;
	}


	public void setMedicationDosage(String medicationDosage) {
		this.medicationDosage = medicationDosage;
	}
	
}
