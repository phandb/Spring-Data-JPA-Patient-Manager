package com.javaprojects.springboot.jpa.patientmanager.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="patients")

public class Patient {

	//Define fields

	//Map to appropriate column in table patients
	//name = "column name" in database
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="middle_name")
	private String middleName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="gender")
	private String gender;
	
	//@Temporal(TemporalType.DATE)
	@Column(name="date_of_birth")
	private Date dateOfBirth;
	
	@Column(name="address")
	private String address;
	
	/****************Define relationship with medication*************************
	
	The Second Best way to model a one to many relationship is to use
	Bidirectional @OneToMany suggested by Vlad Mihalcea.  */
	
	/*@OneToMany(mappedBy = "patient",
				cascade = { CascadeType.PERSIST,
							CascadeType.MERGE,
							CascadeType.DETACH,
							CascadeType.REFRESH 
						})
	*/
	@OneToMany(mappedBy = "patient",
				cascade = CascadeType.ALL,
				orphanRemoval = true)
	private Set<Medication> medications = new HashSet<Medication>();
	

	public Set<Medication> getMedications() {
		return medications;
	}


	public void setMedications(Set<Medication> medications) {
		this.medications = medications;
	}
	
	public void addMedication(Medication medication) {
				
		medications.add(medication);
		medication.setPatient(this);
	
	}
	
	public void removeMedication(Medication medication) {
		medications.remove(medication);
		medication.setPatient(null);
	}
	
	
	/*******************************************************************/
	//Mapping Many To Many relationship with physicians table
	@ManyToMany(fetch=FetchType.LAZY,
				cascade= {CascadeType.PERSIST, 
						  CascadeType.MERGE
						   })
	//@ManyToMany(cascade = CascadeType.ALL)
	//@JsonBackReference
	@JoinTable(
			name="patients_physicians",
			joinColumns=@JoinColumn(name="patient_id", referencedColumnName = "id"),
			inverseJoinColumns=@JoinColumn(name="physician_id", referencedColumnName = "id")
			)
	private Set<Physician> physicians = new HashSet<Physician>();
	
	//Setter and getter for physicians


	public Set<Physician> getPhysicians() {
		return physicians;
	}


	public void setPhysicians(Set<Physician> physicians) {
		this.physicians = physicians;
	}
	
	//Convenience Methods
	public void addPhysician(Physician physician) {
		
		physicians.add(physician);
		physician.getPatients().add(this);
	}
	
	public void removePhysician(Physician physician) {
		
		physicians.remove(physician);
		physician.getPatients().remove(this);
	}
	
		
	
	/******Mapping Many To Many relationship with pharmacies table**********/
	//Mapping Many To Many relationship with pharmacies table
	//No CascadeType.REMOVE since we don't want to delete a patient
	@ManyToMany(fetch=FetchType.LAZY,
				cascade= {CascadeType.PERSIST, CascadeType.MERGE,
						CascadeType.DETACH, CascadeType.REFRESH
						})
	
	//@ManyToMany(cascade = CascadeType.ALL)
	//@JsonBackReference
	@JoinTable(
			name = "patient_pharmacy",
			joinColumns=@JoinColumn(name="patient_id", referencedColumnName ="id"),
			inverseJoinColumns=@JoinColumn(name="pharmacy_id", referencedColumnName="id")
			)
	private Set<Pharmacy> pharmacies = new HashSet<Pharmacy>();
	
	//Setter and getter for pharmacies
	public Set<Pharmacy> getPharmacies() {
		return pharmacies;
	}


	public void setPharmacies(Set<Pharmacy> pharmacies) {
		this.pharmacies = pharmacies;
	}
	
	//Convenience Methods
		public void addPharmacy(Pharmacy pharmacy) {
			
			pharmacies.add(pharmacy);
			pharmacy.getPatients().add(this);
		}
		
		public void removePharmacy(Pharmacy pharmacy) {
			
			pharmacies.remove(pharmacy);
			pharmacy.getPatients().remove(this);
		}
	/****************************************************************/
		

		@Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof Patient)) return false;
	        return id != 0 && id == (((Patient) o).id);
	    }
	 
	    @Override
	    public int hashCode() {
	        return 31;
	    }

	
	/*****************************************************************/
	
	//Defining Constructor
	public Patient() {
		
	}

	public Patient(String firstName, String middleName, String lastName, String gender, Date dateOfBirth,
			String address) {
		
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
	}
	
	//Get Setter Setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	//Define ToString Method

	@Override
	public String toString() {
		return "Patient [id=" + id + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName="
				+ lastName + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + ", address=" + address + "]";
	}
	
	
	//Patient full name
	public String getPatientFullName() {
		String fullName;
		if(middleName  != null) {
			fullName = firstName + " " + middleName + " " + lastName;
		}else {
			middleName = middleName + " ";
			fullName = firstName + " " + middleName + " " + lastName;
		}
		
		
		return fullName;
	}
	



	
	

}
