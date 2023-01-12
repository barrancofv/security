package com.master.practica.dto;

import java.util.List;

public record UserDto(long id, String username, String mail, List<ReviewDto> reviews, List<RoleDto> roles) {}
