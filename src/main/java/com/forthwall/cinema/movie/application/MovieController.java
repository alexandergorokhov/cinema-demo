package com.forthwall.cinema.movie.application;

import com.forthwall.cinema.movie.application.view.response.MovieViewResponse;
import com.forthwall.cinema.movie.application.view.response.MovieTimeViewResponse;
import com.forthwall.cinema.movie.service.MovieService;
import com.forthwall.cinema.movie.service.dto.MovieDto;
import com.forthwall.cinema.movie.service.dto.MovieTimeDto;
import com.forthwall.cinema.movie.application.view.request.MovieTimeViewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsibly for managing movie request.
 * This controller should handle all the movie request
 */
@RestController
public class MovieController {

    /**
     * Movie service responsible for managing movie informaiton
     */
    MovieService movieServiceImpl;
    private static final String MOVIES = "movies";
    private static final String MOVIES_SESSION = "movieSession";


    @Autowired
    public MovieController(MovieService movieServiceImpl) {
        this.movieServiceImpl = movieServiceImpl;
    }

    /**
     * @param idMovie Id of the movie to be viewed
     * @param date    Date of the movie to which the avalibility wants to be known.
     * @return {@link MovieTimeViewRequest}. Is a response object that contains
     * information about movie time, rooms , prices  for desired date.
     * <>200</> - return information
     * <>204<</> - no informartion was found
     * <>500</> -  error
     */
    @GetMapping(MOVIES_SESSION)
    public ResponseEntity<MovieTimeViewResponse> movieSessionsByDate(
        @RequestParam(value = "idMovie") Long idMovie,
        @RequestParam(value = "date") String date) {
        try {
            MovieTimeDto dto = new MovieTimeDto();
            dto.setIdMovie(idMovie);
            dto.setDateTimeMovie(LocalDateTime.parse(date));
            MovieTimeDto dtoResponse = movieServiceImpl.getMovieByIdAndDate(dto);
            if (dtoResponse != null) {
                MovieTimeViewResponse response = new MovieTimeViewResponse(dtoResponse.getIdMovie(),
                    dtoResponse.getName(), dtoResponse.getRooms(), dtoResponse.getDateTimeMovie());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(MOVIES)
    public ResponseEntity<List<MovieViewResponse>> movies() {
        try {
            List <MovieDto> movies = movieServiceImpl.getAllMovies();
            List<MovieViewResponse> response = new ArrayList<>();
            for (MovieDto movie : movieServiceImpl.getAllMovies()){
                MovieViewResponse responseMovie = new MovieViewResponse();
                responseMovie.setIdMovie(movie.getIdMovie());
                responseMovie.setName(movie.getNameMovie());
                response.add(responseMovie);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        }


    }

}
