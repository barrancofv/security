package com.master.practica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.master.practica.model.Role;
import com.master.practica.model.User;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByName(String rolname);
	
}
