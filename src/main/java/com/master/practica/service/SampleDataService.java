package com.master.practica.service;

import java.util.ArrayList;
import java.util.List;

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
		
		Role r1 = new Role();
		r1.setName("ROLE_ADMIN");
		Role r2 = new Role();
		r2.setName("ROLE_USER");
		rolService.save(r1);
		rolService.save(r2);
		
		
		List<Role> roles = new ArrayList<>();
		roles.add(r1);

		User p = new User();
		p.setUsername("Pepe");
		p.setEmail("pepe@gmail.com");
		p.setPassword("$2a$12$QyTxXohd5RzP/iAQHtPhU.IZhAAqtKmcX/kPzubhkr2Jaa93BPkSK");
		p.setEnabled(true);
		p.setRoles(roles);
		userService.save(p);
		
		Review r = new Review();
		r.setComment("El libro es una maravilla");
		r.setRate(5);
		r.setUsername("Pepe");
		
		List<Review> reviews = new ArrayList<>();
		reviews.add(r);
		
		Book b = new Book();
		b.setTitle("Reina Roja");
		b.setArgument("Es un libro de misterio");
		b.setEditorial("Planeta");
		b.setDate("2022");
		b.setReviews(reviews);
		bookService.save(b);
		
		
		
		
		
	}
}
