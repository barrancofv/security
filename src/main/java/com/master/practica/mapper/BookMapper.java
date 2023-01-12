package com.master.practica.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.master.practica.dto.BookDto;
import com.master.practica.dto.BookReviewDto;
import com.master.practica.model.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {
    default BookDto bookToDto(Book book) {
        if ( book == null ) {
            return null;
        }

        long id = 0L;
        String title = null;
        String argument = null;
        String editorial = null;
        String date = null;
        List<BookReviewDto> reviews = null;

        id = book.getId();
        title = book.getTitle();
        argument = book.getArgument();
        editorial = book.getEditorial();
        date = book.getDate();

        if (book.getReviews().isEmpty()) {
            reviews = Collections.emptyList();
        } else {
            reviews = book.getReviews().stream().map(review -> new BookReviewDto(review.getComment(), review.getUser().getId())).collect(Collectors.toList());
        }

        BookDto bookDto = new BookDto( id, title, argument, editorial, date, reviews );

        return bookDto;
    }

    @InheritInverseConfiguration
    Book bookToDomain(BookDto bookDto);
}
