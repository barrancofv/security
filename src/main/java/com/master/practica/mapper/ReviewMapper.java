package com.master.practica.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.master.practica.dto.ReviewDto;
import com.master.practica.model.Review;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mappings({
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "book.id", target = "bookId")
    })
    ReviewDto reviewToDto(Review review);
    @InheritInverseConfiguration
    Review reviewToDomain(ReviewDto reviewDto);
}
