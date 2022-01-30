package com.forthwall.cinema.movie.service;

import com.forthwall.cinema.movie.service.dto.MovieDto;
import com.forthwall.cinema.movie.service.dto.MovieTimeDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public interface MovieService {

    MovieTimeDto getMovieByIdAndDate(MovieTimeDto movieTimeDto);

    List<MovieDto> getAllMovies();
}
