package com.javaprojects.springboot.jpa.patientmanager.model;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;



@Entity
@Table(name="physicians")

public class Physician {
	
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
	
	@Column(name="name")
	private String physicianName;
	
	@Column(name="phone")
	private String physicianPhone;
	
	@Column(name="address")
	private String physicianAddress;
	
	@Column(name="specialty")
	private String physicianSpecialty;
	
	/**********check box list of physician*****/
	/*
	private List<Integer> physicianCheckboxList;
	

	public List<Integer> getPhysicianCheckboxList() {
		return physicianCheckboxList;
	}

	public void setPhysicianCheckboxList(List<Integer> physicianCheckboxList) {
		this.physicianCheckboxList = physicianCheckboxList;
	}
	*/
	
	
	/************Relationship with patients table****************************/
	
	//a doctor can have many patients, and  a patient can have many doctors
	//No delete cascade type.  Can't not delete doctor once a patient is deleted
	/*@ManyToMany(fetch = FetchType.LAZY,
				cascade= {CascadeType.PERSIST, CascadeType.MERGE,
						 CascadeType.DETACH, CascadeType.REFRESH
						 },
				mappedBy = "physicians")
	*/
	@ManyToMany (mappedBy = "physicians")
	/*@JoinTable(
			name="patients_physicians",
			joinColumns=@JoinColumn(name="physician_id", referencedColumnName ="id"),
			inverseJoinColumns=@JoinColumn(name="patient_id", referencedColumnName ="id")
			)
			*/
	private Set<Patient> patients = new HashSet<Patient>();
	
	public Set<Patient> getPatients() {
		return this.patients;
	}

	public void setPatients(Set<Patient> patients) {
		this.patients = patients;
	}
	

	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Physician physician = (Physician) o;
	        return Objects.equals(physicianName, physician.physicianName);
	    }
	 
	    @Override
	    public int hashCode() {
	        return Objects.hash(physicianName);
	    }
	
	/*****************************************************************/
	//Constructors
	public Physician() {
		
	}

	/**
	 * @param physicanName
	 * @param physicianPhone
	 * @param physicianAddress
	 * @param physicianSpecialty
	 */
	public Physician(String physicianName, String physicianPhone, String physicianAddress, String physicianSpecialty) {
		this.physicianName = physicianName;
		this.physicianPhone = physicianPhone;
		this.physicianAddress = physicianAddress;
		this.physicianSpecialty = physicianSpecialty;
	}
	
	//Define getter and setter

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhysicianName() {
		return physicianName;
	}

	public void setPhysicianName(String physicanName) {
		this.physicianName = physicanName;
	}

	public String getPhysicianPhone() {
		return physicianPhone;
	}

	public void setPhysicianPhone(String physicianPhone) {
		this.physicianPhone = physicianPhone;
	}

	public String getPhysicianAddress() {
		return physicianAddress;
	}

	public void setPhysicianAddress(String physicianAddress) {
		this.physicianAddress = physicianAddress;
	}

	public String getPhysicianSpecialty() {
		return physicianSpecialty;
	}

	public void setPhysicianSpecialty(String physicianSpecialty) {
		this.physicianSpecialty = physicianSpecialty;
	}

	

	
	@Override
	public String toString() {
		return "Physician [id=" + id + ", physicianName=" + physicianName + ", physicianPhone=" + physicianPhone
				+ ", physicianAddress=" + physicianAddress + ", physicianSpecialty=" + physicianSpecialty + "]";
	}

	
	

}

