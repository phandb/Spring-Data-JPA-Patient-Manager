package com.javaprojects.springboot.jpa.patientmanager.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaprojects.springboot.jpa.patientmanager.model.Role;
import com.javaprojects.springboot.jpa.patientmanager.model.User;
import com.javaprojects.springboot.jpa.patientmanager.repository.RoleRepository;
import com.javaprojects.springboot.jpa.patientmanager.repository.UserRepository;
import com.javaprojects.springboot.jpa.patientmanager.user.CustomRegisterUser;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/*
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	*/
	
	/*
	@Autowired
	public UserServiceImpl(UserRepository userRepository,
						   RoleRepository roleRepository,
						   BCryptPasswordEncoder bCryptPasswordEncoder) {
		
		this.userRepository=userRepository;
		this.roleRepository=roleRepository;
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
	}
	*/
	@Override
	public User findByUsername(String userName) {
		
		return userRepository.findByUserName(userName);
	}

	@Override
	public void saveUser(CustomRegisterUser custRegUser) {
		
		User user = new User();
		
		// assign user details to the user object
		user.setUserName(custRegUser.getUserName());
		user.setPassword(bCryptPasswordEncoder.encode(custRegUser.getPassword()));
		user.setFirstName(custRegUser.getFirstName());
		user.setLastName(custRegUser.getLastName());
		user.setEmail(custRegUser.getEmail());
		//user.setActive(1);
		
		//then give user default role of "Guest"
		user.setRoles(Arrays.asList(roleRepository.findByRoleName("Guest")));
		
		//save user in database
		userRepository.save(user);
		
		
	}
	
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
	}


	

}
