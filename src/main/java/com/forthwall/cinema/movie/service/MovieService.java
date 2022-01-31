package com.forthwall.cinema.movie.service;

import com.forthwall.cinema.movie.service.dto.MovieDescriptionDto;
import com.forthwall.cinema.movie.service.dto.MovieDto;
import com.forthwall.cinema.movie.service.dto.MovieTimeSessionDto;
import com.forthwall.cinema.movie.service.dto.ReviewDto;

import java.time.LocalDate;
import java.util.List;

public interface MovieService {

    List<MovieDto> getAllMovies();

    List<MovieTimeSessionDto> getMoviesByDate(LocalDate date);

    void updateMovieTimeSession(MovieTimeSessionDto timeSessionDto);

    void updateMoviePriceSession(MovieTimeSessionDto timeSessionDto);

    void saveReview(ReviewDto dto);

    MovieDescriptionDto getMovieDescriptionById(Long movieId);

    MovieDescriptionDto getMovieDescriptionByExternalId(String idMovie);
}
