package com.master.practica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.master.practica.model.Role;
import com.master.practica.repository.RoleRepository;

@Component
public class RolService {
	
	@Autowired
	private RoleRepository rolRepository;
	
	public void save(Role rol) {
		rolRepository.save(rol);		
	}
	
}
