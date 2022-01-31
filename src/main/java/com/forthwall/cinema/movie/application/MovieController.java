package com.forthwall.cinema.movie.application;

import com.forthwall.cinema.movie.application.view.request.ReviewRequest;
import com.forthwall.cinema.movie.application.view.response.MovieDescriptionResponse;
import com.forthwall.cinema.movie.application.view.response.MovieViewResponse;
import com.forthwall.cinema.movie.application.view.response.MovieTimeSessionViewResponse;
import com.forthwall.cinema.movie.service.MovieService;
import com.forthwall.cinema.movie.service.dto.MovieDescriptionDto;
import com.forthwall.cinema.movie.service.dto.MovieDto;
import com.forthwall.cinema.movie.service.dto.MovieTimeSessionDto;
import com.forthwall.cinema.movie.application.view.request.MovieTimeRequest;
import com.forthwall.cinema.movie.service.dto.ReviewDto;
import com.forthwall.cinema.movie.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
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
    private static final String MOVIE = "movie";
    private static final String REVIEW = "review";
    private static final String DESCRIPTION = "description";



    @Autowired
    public MovieController(MovieService movieServiceImpl) {
        this.movieServiceImpl = movieServiceImpl;
    }

    /**
     * @param idMovie Id of the movie to be viewed
     * @param date    Date of the movie to which the availability wants to be known.
     * @return {@link MovieTimeRequest}. Is a response object that contains
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
                return new ResponseEntity<>(response, HttpStatus.OK);
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

    /**
     * @param idSession       id of the session in which the movie will shown
     * @param dateTimeSession date and wimt to which the movie should be updated
     * @param price           price to which the movie should be updated.
     * @return Response entity
     * <200>OK<200/>
     * <500>Error</500>
     */
    @PutMapping(MOVIE)
    public ResponseEntity updateMovie(@RequestParam(name = "idSession") Long idSession,
        @RequestParam(name = "dateTimeSession", required = false) String dateTimeSession,
        @RequestParam(name = "price", required = false) BigDecimal price) {
        try {
            MovieTimeSessionDto timeSessionDto = new MovieTimeSessionDto();
            timeSessionDto.setIdSession(idSession);

            if (dateTimeSession != null) {
                timeSessionDto.setTimeMovie(LocalDateTime.parse(dateTimeSession, DateUtils.getDateTimeFormatter()));
                timeSessionDto.setDateMovie(LocalDateTime.parse(dateTimeSession, DateUtils.getDateTimeFormatter()).toLocalDate());
                movieServiceImpl.updateMovieTimeSession(timeSessionDto);
            }
            if (price != null) {
                timeSessionDto.setPrice(price);
                movieServiceImpl.updateMoviePriceSession(timeSessionDto);
            }
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param request {@link ReviewRequest} body request  containing the review information
     * @return Response Entity
     * <200>Saved</200>
     * <500>Error</500>
     */
    @PostMapping(REVIEW)
    public ResponseEntity createReview(@RequestBody ReviewRequest request) {
        try {
            ReviewDto dto = new ReviewDto();
            dto.setComment(request.getComment());
            dto.setStars(request.getStars());
            dto.setIdMovie(request.getIdMovie());
            movieServiceImpl.saveReview(dto);
            return new ResponseEntity(HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method if provided just internal movieId with retrieve the exteranl
     * movie id form the DB and return a description provided by external API.
     *  If external identificator is provided directly, it will consult the exteranl API without
     *  local DB validation.
     * @param idMovie Internal id movie
     * @param iMBDmovieId External applicaiton Id movie
     * @return Response Entity.
     * <200>MovieDescriptionResponse</200>
     * <400>Bad Request</400>
     * <500>Server Error</500>
     */
    @GetMapping(DESCRIPTION)
    public ResponseEntity<MovieDescriptionResponse> getMovieDescriptionResponse(@RequestParam(name = "idMovie", required = false) Long idMovie,
        @RequestParam(name = "idMovieIMDb", required = false) String iMBDmovieId) {

        try {
            if (idMovie != null) {
                MovieDescriptionDto dto = movieServiceImpl.getMovieDescriptionById(idMovie);
                MovieDescriptionResponse response = new MovieDescriptionResponse();
                response.setName(dto.getName());
                response.setDescription(dto.getDescription());
                response.setReleaseDate(dto.getReleaseDate());
                response.setIMDGRating(dto.getIMDGRating());
                response.setRuntime(dto.getRuntime());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            if (iMBDmovieId != null) {
                MovieDescriptionDto dto = movieServiceImpl.getMovieDescriptionByExternalId(iMBDmovieId);
                MovieDescriptionResponse response = new MovieDescriptionResponse();
                response.setName(dto.getName());
                response.setDescription(dto.getDescription());
                response.setReleaseDate(dto.getReleaseDate());
                response.setIMDGRating(dto.getIMDGRating());
                response.setRuntime(dto.getRuntime());
                ArrayList ratingList = new ArrayList();
                for (MovieDescriptionDto.Rating ratingDto: dto.getRating()
                ) {
                    MovieDescriptionResponse.Rating rating = new MovieDescriptionResponse.Rating() ;
                    rating.setSource(ratingDto.getSource());
                    rating.setValue(ratingDto.getValue());
                    ratingList.add(rating);
                }
                response.setRating(ratingList);

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

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
