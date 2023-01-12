package com.master.practica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.master.practica.model.Book;
import com.master.practica.model.Review;
import com.master.practica.model.Role;
import com.master.practica.model.User;

import jakarta.annotation.PostConstruct;

@Service
public class SampleDataService {

	@Autowired
	private UserService userService; 
	
	@Autowired
	private BookService bookService; 
	
	@Autowired
	private ReviewService reviewService; 
	
	@Autowired
	private RolService rolService;
	
	@PostConstruct
	public void init() {

		User p = new User();
		p.setUsername("Pepe");
		p.setMail("pepe@gmail.com");
		p.setPassword("$2a$12$QyTxXohd5RzP/iAQHtPhU.IZhAAqtKmcX/kPzubhkr2Jaa93BPkSK");
		p.setEnabled(true);
		userService.save(p);
		
		Book b = new Book();
		b.setTitle("Reina Roja");
		b.setArgument("Es un libro de misterio");
		b.setEditorial("Planeta");
		b.setDate("2022");
		bookService.save(b);
		
		Review r = new Review();
		r.setComment("El libro es una maravilla");
		r.setRate(5);
		r.setUsername("Pepe");
		reviewService.save(r, 1, 1);
		
		Role r1 = new Role();
		r1.setName("ADMIN");
		Role r2 = new Role();
		r2.setName("USER");
		
		rolService.save(r1);
		rolService.save(r2);
		
		userService.addRoleToUser("Pepe", "ADMIN");
		
	}
}
