package com.master.practica.dto;

import java.util.List;

public record BookDto(long id, String title, String argument, String editorial, String date, List<BookReviewDto> reviews) {}
