package com.forthwall.cinema.movie.application;

import com.forthwall.cinema.movie.application.view.request.ReviewRequest;
import com.forthwall.cinema.movie.application.view.response.MovieDescriptionResponse;
import com.forthwall.cinema.movie.application.view.response.MovieViewResponse;
import com.forthwall.cinema.movie.application.view.response.MovieTimeSessionViewResponse;
import com.forthwall.cinema.movie.service.MovieService;
import com.forthwall.cinema.movie.service.TokeService;
import com.forthwall.cinema.movie.service.dto.MovieDescriptionDto;
import com.forthwall.cinema.movie.service.dto.MovieDto;
import com.forthwall.cinema.movie.service.dto.MovieTimeSessionDto;
import com.forthwall.cinema.movie.application.view.request.MovieTimeRequest;
import com.forthwall.cinema.movie.service.dto.ReviewDto;
import com.forthwall.cinema.movie.utils.DateUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

/**
 * Controller responsible for managing movie request.
 * This controller should handle all the movie request
 */
@Slf4j
@RestController
public class MovieController {

    /**
     * Movie service responsible for managing movie information
     */
    MovieService movieServiceImpl;
    TokeService tokeService;
    private static final String MOVIES = "movies";
    private static final String MOVIES_SESSION = "movieSession";
    private static final String MOVIE = "movie";
    private static final String REVIEW = "review";
    private static final String DESCRIPTION = "description";
    private static final String ADMIN = "admin/";


    @Autowired
    public MovieController(MovieService movieServiceImpl, TokeService tokeService) {
        this.movieServiceImpl = movieServiceImpl;
        this.tokeService = tokeService;
    }

    /**
     * This endpoint will provide users with information about all the movie sessions (movie session = movie played, time ,room, price) for the
     * specified date.
     * <p>
     * EX: curl -X GET \
     * 'http://localhost:8080/movieSession?date=2022-02-03' \
     * -H 'cache-control: no-cache' \
     *
     * @param date    Date of the movie to which the availability wants to be known.
     * @return {@link MovieTimeRequest}. Is a response object that contains
     * information about movie time, rooms , prices  for desired date.
     * <>200</> - return information of movie sessions
     * <>500</> -  error
     */
    @Operation(summary = "This endpoint will provide users with information about all the movie sessions (movie session = movie played, time ,room, price) for the specified date.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Return information about all movie sesisons",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = MovieTimeSessionViewResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Error", content = @Content
        )})
    @GetMapping(value = MOVIES_SESSION, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MovieTimeSessionViewResponse>> movieSessionsByDate(
        @RequestParam(value = "date", required = false) String date) {
        try {
            MovieTimeSessionDto dto = new MovieTimeSessionDto();
            dto.setTimeMovie(LocalDateTime.parse(date, DateUtils.getDateTimeFormatter()));
            List<MovieTimeSessionDto> dtoResponse = movieServiceImpl.getMoviesByDate(LocalDate.parse(date));
            List<MovieTimeSessionViewResponse> response = prepareListOfMoviesSessionResponse(dtoResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This endpoint will provide all the movies (id, name) available in the database.
     * Mostly used for internal testing.
     * EX: curl -X GET \
     * http://localhost:8080/movies \
     * -H 'cache-control: no-cache' \
     * -H 'content-type: application/json'
     *
     * @return List of all the movies {@link MovieTimeSessionViewResponse}
     * <>200</> OK
     * <>500</> Internal Server Error
     */
    @Operation(summary = "This endpoint will provide all the movies (id, name) available in the database.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Return information about all movie sessions",
            content = {@Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = MovieTimeSessionViewResponse.class))
            )}),
        @ApiResponse(responseCode = "500", description = "Error", content = @Content
        )})
    @GetMapping(value = MOVIES, produces = MediaType.APPLICATION_JSON_VALUE)
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
     * This endpoint is used to update a session price and dateTime. It means that a movie is shown in a certain
     * movie session which contains the price of the session. The price will vary depending if the Session
     * if on Friday night or at 14 on a working day.
     * <p>
     * Ex for price update :curl -X PUT \
     * 'http://localhost:8080/admin/movie?idSession=2&price=50' \
     * -H 'bearer: 1234' \
     * -H 'cache-control: no-cache' \
     * Ex for time update:
     * curl -X PUT \
     * 'http://localhost:8080/admin/movie?idSession=2&dateTimeSession=2030-04-01%2015%3A20%3A00' \
     * -H 'bearer: 1234' \
     * -H 'cache-control: no-cache' \
     *
     * @param idSession       id of the session in which the movie will shown
     * @param dateTimeSession date and wimt to which the movie should be updated
     * @param price           price to which the movie should be updated.
     * @return Response entity
     * <200>OK<200/>
     * <403>Unauthorized</403>
     * <500>Error</500>
     */
    @Operation(summary = " This endpoint is used to update a session price and dateTime. "
        + "It means that a movie is shown in a certain movie session which contains the price of the session.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "operation successful",
            content = @Content),
        @ApiResponse(responseCode = "401", description = "Not Authorized", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error", content = @Content
        )})
    @PutMapping(value = ADMIN + MOVIE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateMovie(@RequestHeader(value = "Bearer") String token,
        @RequestParam(name = "idSession") Long idSession,
        @RequestParam(name = "dateTimeSession", required = false) String dateTimeSession,
        @RequestParam(name = "price", required = false) BigDecimal price
    ) {
        log.info("Admin request arrived");
        if (token != null && token.equals(tokeService.getToken())) {
            log.debug("Token validated");
            try {
                MovieTimeSessionDto timeSessionDto = new MovieTimeSessionDto();
                timeSessionDto.setIdSession(idSession);

                if (dateTimeSession != null) {
                    log.debug("Preparing to update date and time of the Session");
                    timeSessionDto.setTimeMovie(LocalDateTime.parse(dateTimeSession, DateUtils.getDateTimeFormatter()));
                    timeSessionDto.setDateMovie(LocalDateTime.parse(dateTimeSession, DateUtils.getDateTimeFormatter()).toLocalDate());
                    log.debug("Calling movie service for update");
                    movieServiceImpl.updateMovieTimeSession(timeSessionDto);
                }
                if (price != null) {
                    log.debug("Preparing to update price of the Session");
                    timeSessionDto.setPrice(price);
                    movieServiceImpl.updateMoviePriceSession(timeSessionDto);
                }
                return new ResponseEntity<>(HttpStatus.OK);

            } catch (RuntimeException e) {
                log.warn(e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            log.warn("Token unauthorized");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * This endpoint is used to post reviews.
     * Ex: curl -X POST \
     * http://localhost:8080/review \
     * -H 'cache-control: no-cache' \
     * -H 'content-type: application/json' \
     * -d '{
     * "idMovie":3,
     * "stars":1,
     * "comment":"Nice"
     * }'
     *
     * @param request {@link ReviewRequest} body request  containing the review information
     * @return Response Entity
     * <200>Saved</200>
     * <500>Error</500>
     */
    @Operation(summary = "This endpoint is used to post reviews.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "operation successful",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error", content = @Content
        )})
    @PostMapping(value = REVIEW, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createReview(@Valid @RequestBody ReviewRequest request) {
        try {
            ReviewDto dto = new ReviewDto();
            dto.setComment(request.getComment());
            dto.setStars(request.getStars());
            dto.setIdMovie(request.getIdMovie());
            movieServiceImpl.saveReview(dto);
        } catch (RuntimeException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);

    }

    /**
     * This endpoint if provided just internal movieId will retrieve the external
     * movie id form the DB and return a description provided by external API.
     * EX: curl -X GET \
     * 'http://localhost:8080/description?idMovie=3' \
     * -H 'cache-control: no-cache' \
     * If external identification is provided directly, it will consult the externalAPI without
     * local DB validation.
     * Ex: curl -X GET \
     * 'http://localhost:8080/description?idMovieIMDb=tt0232500' \
     * -H 'cache-control: no-cache' \
     *
     * @param idMovie     Internal id movie
     * @param idMovieIMDb External applicaiton Id movie
     * @return Response Entity.
     * <200>MovieDescriptionResponse</200>
     * <400>Bad Request</400>
     * <500>Server Error</500>
     */
    @Operation(summary = "This endpoint if provided just internal movieId will retrieve the external "
        + "movie id form the DB and return a description provided by external API.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "operation successful",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = MovieDescriptionResponse.class))}),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content
        ),
        @ApiResponse(responseCode = "500", description = "Error", content = @Content
        )})
    @GetMapping(value = DESCRIPTION, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieDescriptionResponse> getMovieDescriptionResponse(@RequestParam(name = "idMovie", required = false) Long idMovie,
        @RequestParam(name = "idMovieIMDb", required = false) String idMovieIMDb) {

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
            if (idMovieIMDb != null) {
                MovieDescriptionDto dto = movieServiceImpl.getMovieDescriptionByExternalId(idMovieIMDb);
                MovieDescriptionResponse response = new MovieDescriptionResponse();
                response.setName(dto.getName());
                response.setDescription(dto.getDescription());
                response.setReleaseDate(dto.getReleaseDate());
                response.setIMDGRating(dto.getIMDGRating());
                response.setRuntime(dto.getRuntime());
                ArrayList ratingList = new ArrayList();
                for (MovieDescriptionDto.Rating ratingDto : dto.getRating()
                ) {
                    MovieDescriptionResponse.Rating rating = new MovieDescriptionResponse.Rating();
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
            responseSession.setPrice(movie.getPrice());

            response.add(responseSession);
        }
        return response;
    }

}
