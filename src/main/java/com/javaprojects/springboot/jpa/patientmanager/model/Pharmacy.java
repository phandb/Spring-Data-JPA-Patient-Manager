package com.javaprojects.springboot.jpa.patientmanager.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;



@Entity
@Table(name="pharmacies")

public class Pharmacy {

	//define the fields
	//annotate the fields with db column names
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	//@NaturalId
	@Column(name="name")
	private String pharmacyName;
	
	@Column(name="phone")
	private String pharmacyPhone;
	
	@Column(name="address")
	private String pharmacyAddress;
	
	
	/***************************************************************************/
	
	 // Set up Many to Many relationship with patients table using FK patient_id

	/*@ManyToMany(fetch=FetchType.LAZY,
			cascade= {CascadeType.PERSIST, CascadeType.MERGE,
					CascadeType.DETACH, CascadeType.REFRESH	
					},
			mappedBy="pharmacies")
		*/		
	@ManyToMany(mappedBy="pharmacies")
	/*@JoinTable(
		name = "patient_pharmacy",
		joinColumns=@JoinColumn(name="pharmacy_id", referencedColumnName ="id"),
		inverseJoinColumns=@JoinColumn(name="patient_id", referencedColumnName ="id")
		)*/
	private Set<Patient> patients = new HashSet<>();
	
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
	        Pharmacy pharmacy = (Pharmacy) o;
	        return Objects.equals(pharmacyName, pharmacy.pharmacyName);
	    }
	 
	    @Override
	    public int hashCode() {
	        return Objects.hash(pharmacyName);
	    }
	
	
	
	
	/***************************************************************************/
	
	//create constructors
	
	public Pharmacy() {
	
	}


	public Pharmacy(String pharmacyName, String pharmacyPhone, String pharmacyAddress) {
		this.pharmacyName = pharmacyName;
		this.pharmacyPhone = pharmacyPhone;
		this.pharmacyAddress = pharmacyAddress;
		
	}
	
	//generate getter/setter methods
	
	public int getId() {
		return this.id;
	}
	

	public void setId(int id) {
		this.id = id;
	}


	public String getPharmacyName() {
		return pharmacyName;
	}


	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}


	public String getPharmacyPhone() {
		return pharmacyPhone;
	}


	public void setPharmacyPhone(String pharmacyPhone) {
		this.pharmacyPhone = pharmacyPhone;
	}


	public String getPharmacyAddress() {
		return pharmacyAddress;
	}


	public void setPharmacyAddress(String pharmacyAddress) {
		this.pharmacyAddress = pharmacyAddress;
	}




	//generate toString() method
	@Override
	public String toString() {
		return "Pharmacy [id=" + id + ", pharmacyName=" + pharmacyName + ", pharmacyPhone=" + pharmacyPhone
				+ ", pharmacyAddress=" + pharmacyAddress + ", patientId_FK=" + patients + "]";
	}
	

}
