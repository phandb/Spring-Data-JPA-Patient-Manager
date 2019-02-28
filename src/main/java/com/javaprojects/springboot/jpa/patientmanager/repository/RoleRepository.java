package com.javaprojects.springboot.jpa.patientmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaprojects.springboot.jpa.patientmanager.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	public Role findByRoleName(String roleName);

}
