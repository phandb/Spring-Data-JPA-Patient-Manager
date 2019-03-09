package com.javaprojects.springboot.jpa.patientmanager.repository;

import org.hibernate.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.javaprojects.springboot.jpa.patientmanager.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	//@Query Query<User> theQuery = currentSession.createQuery("from User where userName=:uName", User.class);
	//theQuery.setParameter("uName", theUserName);
	
	User findByUserName(String userName);
	
	//void save(User user);

}
