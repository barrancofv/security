package com.master.practica.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.master.practica.model.Book;
import com.master.practica.model.BookPartial;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
	@Query(value = "Select NEW com.master.practica.model.BookPartial(b.id, b.title) FROM Book as b")
	Page<BookPartial> findAllPartial(Pageable pageable);
}