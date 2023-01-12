package com.master.practica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.master.practica.dto.UserDto;
import com.master.practica.model.User;
import com.master.practica.service.UserService;

@RestController
@RequestMapping("/library/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public ResponseEntity<Page<UserDto>> getUsers(Pageable page) {
		return userService.findAll(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUser(@PathVariable long id) {
		return userService.findById(id);
	}

	@PostMapping("/")
	public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
		return userService.createUser(userDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> replaceUser(@RequestBody UserDto updatedUserDto, @PathVariable long id) {
		return userService.replace(updatedUserDto, id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<UserDto> deleteUser(@PathVariable long id) {
		return userService.deleteById(id);
	}

}
