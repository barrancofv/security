package com.master.practica.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.master.practica.dto.ReviewDto;
import com.master.practica.dto.RoleDto;
import com.master.practica.dto.UserDto;
import com.master.practica.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    default UserDto userToDto(User user) {
        if ( user == null ) {
            return null;
        }

        long id = 0L;
        String username = null;
        String mail = null;
        List<ReviewDto> reviews = null;
        List<RoleDto> roles = null;

        id = user.getId();
        username = user.getUsername();
        mail = user.getEmail();

        if (user.getReviews().isEmpty()) {
            reviews = Collections.emptyList();
        } else {
            reviews = user.getReviews().stream()
                    .map(review ->
                            new ReviewDto(review.getId(), review.getUser().getUsername(), review.getComment(), review.getRate(), review.getUser().getId(), review.getBook().getId()))
                    .collect(Collectors.toList());
        }
        
        if (user.getRoles().isEmpty()) {
        	roles = Collections.emptyList();
        } else {
        	roles = user.getRoles().stream()
                    .map(role ->
                            new RoleDto(role.getId(), role.getName()))
                    .collect(Collectors.toList());
        }

        UserDto userDto = new UserDto( id, username, mail, reviews, roles);

        return userDto;
    }

    @InheritInverseConfiguration
    User userToDomain(UserDto userDto);
}
