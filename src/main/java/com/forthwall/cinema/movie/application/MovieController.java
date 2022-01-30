package com.forthwall.cinema.movie.application;

import com.forthwall.cinema.movie.application.view.response.MovieViewResponse;
import com.forthwall.cinema.movie.application.view.response.MovieTimeSessionViewResponse;
import com.forthwall.cinema.movie.service.MovieService;
import com.forthwall.cinema.movie.service.dto.MovieDto;
import com.forthwall.cinema.movie.service.dto.MovieTimeSessionDto;
import com.forthwall.cinema.movie.application.view.request.MovieTimeViewRequest;
import com.forthwall.cinema.movie.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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
     * Movie service responsible for managing movie information
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
     * @param date    Date of the movie to which the availability wants to be known.
     * @return {@link MovieTimeViewRequest}. Is a response object that contains
     * information about movie time, rooms , prices  for desired date.
     * <>200</> - return information
     * <>204<</> - no information was found
     * <>500</> -  error
     */
    @GetMapping(MOVIES_SESSION)
    public ResponseEntity<List<MovieTimeSessionViewResponse>> movieSessionsByDate(
        @RequestParam(value = "idMovie", required = false) Long idMovie,
        @RequestParam(value = "date", required = false) String date) {
        try {
            MovieTimeSessionDto dto = new MovieTimeSessionDto();
            dto.setIdMovie(idMovie);
            dto.setTimeMovie(LocalDateTime.parse(date, DateUtils.getDateTimeFormatter()));
            if (idMovie == null && date != null) {
                List<MovieTimeSessionDto> dtoResponse = movieServiceImpl.getMoviesByDate(LocalDate.parse(date));
                List<MovieTimeSessionViewResponse> response = prepareListOfMoviesSessionResponse(dtoResponse);
                return new ResponseEntity(response, HttpStatus.OK);
            }
            MovieTimeSessionDto dtoResponse = movieServiceImpl.getMovieByIdAndDate(dto);
            if (dtoResponse != null) {
                MovieTimeSessionViewResponse response = new MovieTimeSessionViewResponse(
                    dtoResponse.getName(), dtoResponse.getRooms(), dtoResponse.getDateMovie(), dtoResponse.getTimeMovie().toString());
                return new ResponseEntity(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return List of all the movies {@link MovieTimeSessionViewResponse}
     * <>200</> OK
     * <>500</> Internal Server Error
     */
    @GetMapping(MOVIES)
    public ResponseEntity<List<MovieViewResponse>> movies() {
        try {
            List<MovieDto> movies = movieServiceImpl.getAllMovies();
            List<MovieViewResponse> response;
            response = prepareListOfMoviesResponse(movies);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    private List<MovieViewResponse> prepareListOfMoviesResponse(List<MovieDto> listDto) {
        List<MovieViewResponse> response = new ArrayList<>();
        for (MovieDto movie : listDto) {
            MovieViewResponse responseMovie = new MovieViewResponse();
            responseMovie.setIdMovie(movie.getIdMovie());
            responseMovie.setName(movie.getNameMovie());
            response.add(responseMovie);
        }
        return response;
    }

    private List<MovieTimeSessionViewResponse> prepareListOfMoviesSessionResponse(List<MovieTimeSessionDto> listDto) {
        List<MovieTimeSessionViewResponse> response = new ArrayList<>();
        for (MovieTimeSessionDto movie : listDto) {
            MovieTimeSessionViewResponse responseSession = new MovieTimeSessionViewResponse();
            responseSession.setName(movie.getName());
            responseSession.setRooms(movie.getRooms());
            responseSession.setDateMovie(movie.getDateMovie());
            responseSession.setTimeMovie(movie.getTimeMovie().toLocalTime().toString());

            response.add(responseSession);
        }
        return response;
    }

}
