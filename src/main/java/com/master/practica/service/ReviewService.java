package com.master.practica.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.master.practica.mapper.BookMapper;
import com.master.practica.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.master.practica.dto.ReviewDto;
import com.master.practica.mapper.ReviewMapper;
import com.master.practica.model.Review;
import com.master.practica.repository.ReviewRepository;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReviewMapper reviewMapper;
	@Autowired
	private BookMapper bookMapper;
	@Autowired
	private UserMapper userMapper;
	
	public void save(Review review, long userId, long bookId) {
		review.setUser(userMapper.userToDomain(userService.findById(userId).getBody()));
		review.setBook(bookMapper.bookToDomain(bookService.findById(bookId).getBody()));
		reviewRepository.save(review);
	}

	public ResponseEntity<ReviewDto> findById (long id) {
		Optional<Review> createdReview = reviewRepository.findById(id);
		if (createdReview.isPresent()) {
			return ResponseEntity.ok(reviewMapper.reviewToDto(createdReview.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	public ResponseEntity<Page<ReviewDto>> findAll (Pageable page) {
		Page<ReviewDto> createdReviews = reviewRepository.findAll(page).map(review -> reviewMapper.reviewToDto(review));
		return ResponseEntity.ok(createdReviews);
	}


	public ResponseEntity<List<ReviewDto>> findByUser(long userId) {
		List<ReviewDto> userReviews = reviewRepository.findByUser(userId).stream().map(review -> reviewMapper.reviewToDto(review)).collect(Collectors.toList());
		return ResponseEntity.ok(userReviews);
	}

	public ResponseEntity<Review> replace(ReviewDto updatedReview, long id) {
		Optional<Review> existingReview = reviewRepository.findById(id);
		if (existingReview.isPresent()) {
			Review newReview = reviewMapper.reviewToDomain(updatedReview);
			newReview.setId(id);
			reviewRepository.save(newReview);
			return ResponseEntity.ok(newReview);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	public ResponseEntity<ReviewDto> deleteById(long id) {
		reviewRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<Review> createReview(ReviewDto reviewDto, long userId, long bookId) {
		Review review = reviewMapper.reviewToDomain(reviewDto);
		save(review, userId, bookId);
		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(review.getId()).toUri();
		return ResponseEntity.created(location).body(review);
	}
}
