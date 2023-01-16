package com.master.practica.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.master.practica.dto.BookDto;
import com.master.practica.model.Book;
import com.master.practica.model.BookPartial;
import com.master.practica.service.BookService;

@RestController
@RequestMapping(path="/library/books")
public class BookRestController {

	@Autowired
	private BookService bookService;
	
	@GetMapping(value="/")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<Page<BookDto>> getBooks(Pageable page) {
		return bookService.findAll(page);
	}
	
	@GetMapping(value="/partial/")
	public ResponseEntity<Page<BookPartial>> getBooksPartial(Pageable page) {
		return bookService.findAllPartial(page);
	}

	@GetMapping(value="/{bookId}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<BookDto> getBook(@PathVariable(value = "bookId") Long id) {
		return bookService.findById(id);
	}
	
	@PostMapping("/")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<Book> createBook(@RequestBody BookDto book) {
		return bookService.createBook(book);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity <Book> replaceBook(@RequestBody BookDto updatedBookDto, @PathVariable long id) {
		return bookService.replace(updatedBookDto, id);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<BookDto> deleteBook(@PathVariable long id) {
		return bookService.deleteById(id);
	}

}
