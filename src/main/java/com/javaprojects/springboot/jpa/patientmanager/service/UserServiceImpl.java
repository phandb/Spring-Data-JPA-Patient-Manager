package com.javaprojects.springboot.jpa.patientmanager.service;

import java.util.Arrays;
import java.util.Collection;
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
	
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository,
								   RoleRepository roleRepository) {
		
		this.userRepository=userRepository;
		this.roleRepository=roleRepository;
	}
	
	@Override
	public User findByUserName(String userName) {
		
		return userRepository.findByUserName(userName);
	}

	@Override
	public void save(CustomRegisterUser customRegisterUser) {

		User user = new User();
		// assign user details to the user object
		user.setUserName(customRegisterUser.getUserName());
		user.setPassword(passwordEncoder.encode(customRegisterUser.getPassword()));
		user.setFirstName(customRegisterUser.getFirstName());
		user.setLastName(customRegisterUser.getLastName());
		user.setEmail(customRegisterUser.getEmail());
		
		//then give user default role of "employee"
		user.setRoles(Arrays.asList(roleRepository.findByRoleName("ROLE_EMPLOYEE")));
		
		//save user in the database
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
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}


	

}
