package com.master.practica.dto;

public record ReviewDto(long id, String username, String comment, int rate, long userId, long bookId) {}
