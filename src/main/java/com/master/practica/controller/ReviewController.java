package com.master.practica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.master.practica.dto.ReviewDto;
import com.master.practica.model.Review;
import com.master.practica.service.ReviewService;

@RestController
@RequestMapping("/library/reviews")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@GetMapping("/")
	public ResponseEntity<Page<ReviewDto>> getReviews(@RequestParam(required = false) String reviewsUser, Pageable page) {
		return reviewService.findAll(page);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<ReviewDto> getReview(@PathVariable long id) {
		return reviewService.findById(id);
	}

	@GetMapping("/?user={userId}")
	public ResponseEntity<List<ReviewDto>> getReviewsByUser(@PathVariable long userId) {
		return reviewService.findByUser(userId);
	}
	
	@PostMapping("/{userId}/{bookId}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<Review> createReview(@RequestBody ReviewDto reviewDto, @PathVariable long userId, @PathVariable long bookId) {
		return reviewService.createReview(reviewDto, userId, bookId);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Review> replaceReview(@RequestBody ReviewDto updatedReviewDto, @PathVariable long id) {
		return reviewService.replace(updatedReviewDto, id);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ReviewDto> deleteReview(@PathVariable long id) {
		return reviewService.deleteById(id);
	}

}
