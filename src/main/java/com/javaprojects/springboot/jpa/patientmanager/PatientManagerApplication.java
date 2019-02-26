package com.javaprojects.springboot.jpa.patientmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class PatientManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientManagerApplication.class, args);
	}

}

