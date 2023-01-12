package com.master.practica.service;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.master.practica.dto.BookDto;
import com.master.practica.mapper.BookMapper;
import com.master.practica.model.Book;
import com.master.practica.model.BookPartial;
import com.master.practica.repository.BookRepository;

@Component
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookMapper bookMapper;
	
	public void save(Book book) {
		bookRepository.save(book);		
	}
	
	public ResponseEntity<BookDto> findById (long id) {
		Optional<Book> createdBook = bookRepository.findById(id);
		if (createdBook.isPresent()) {
			return ResponseEntity.ok(bookMapper.bookToDto(createdBook.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	public ResponseEntity<Page<BookDto>> findAll (Pageable page) {
		Page<BookDto> createdBooks = bookRepository.findAll(page).map(book -> bookMapper.bookToDto(book));
		return ResponseEntity.ok(createdBooks);
	}
	
	public ResponseEntity<Page<BookPartial>> findAllPartial (Pageable page) {
		Page<BookPartial> createdBooks = bookRepository.findAllPartial(page);
		return ResponseEntity.ok(createdBooks);
	}

	public ResponseEntity<Book> replace(BookDto updatedBook, long id) {
		Optional<Book> existingBook = bookRepository.findById(id);
		if (existingBook.isPresent()) {
			Book newBook = bookMapper.bookToDomain(updatedBook);
			newBook.setId(id);
			bookRepository.save(newBook);
			return ResponseEntity.ok(newBook);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	public ResponseEntity<BookDto> deleteById(long id) {
		bookRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<Book> createBook(BookDto bookDto) {
		Book book = bookMapper.bookToDomain(bookDto);
		save(book);
		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(book.getId()).toUri();
		return ResponseEntity.created(location).body(book);
	}
	
}
