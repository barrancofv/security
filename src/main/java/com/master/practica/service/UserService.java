package com.master.practica.service;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.master.practica.dto.UserDto;
import com.master.practica.mapper.UserMapper;
import com.master.practica.model.Role;
import com.master.practica.model.User;
import com.master.practica.repository.RoleRepository;
import com.master.practica.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserMapper userMapper;

	public void save(User user) {
		userRepository.save(user);		
	}
	
	public ResponseEntity<UserDto> findById (long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return ResponseEntity.ok(userMapper.userToDto(user.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	public ResponseEntity<Page<UserDto>> findAll (Pageable page) {
		Page<UserDto> createdUsers = userRepository.findAll(page).map( user -> userMapper.userToDto(user));
		return ResponseEntity.ok(createdUsers);
	}
	
	public ResponseEntity<User> replace(UserDto updatedUser, long id) {
		Optional<User> existingUser = userRepository.findById(id);
		if (existingUser.isPresent()) {
			User newUser = userMapper.userToDomain(updatedUser);
			newUser.setId(id);
			userRepository.save(newUser);
			return ResponseEntity.ok(newUser);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	public ResponseEntity<UserDto> deleteById(long id) {
		UserDto user = this.findById(id).getBody();
		if (user.reviews().isEmpty()) {
			userRepository.deleteById(id);
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	public ResponseEntity<User> createUser(UserDto userDto) {
		User user = userMapper.userToDomain(userDto);
		save(user);
		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(location).body(user);
	}
	
	public void addRoleToUser(String username, String rolename) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(rolename);
        user.getRoles().add(role);
        userRepository.save(user);
    }
}
