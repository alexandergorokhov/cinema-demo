package com.forthwall.cinema.movie.service.implementation;

import com.forthwall.cinema.movie.infrastructure.ExternalMovieInformationDao;
import com.forthwall.cinema.movie.infrastructure.MovieDao;
import com.forthwall.cinema.movie.infrastructure.MovieReviewDao;
import com.forthwall.cinema.movie.infrastructure.MovieSessionDao;
import com.forthwall.cinema.movie.infrastructure.api.ExternalWeb;
import com.forthwall.cinema.movie.infrastructure.api.dto.ResponseIMDBDto;
import com.forthwall.cinema.movie.infrastructure.entities.ExternalMovieInformationEntity;
import com.forthwall.cinema.movie.infrastructure.entities.MovieEntity;
import com.forthwall.cinema.movie.infrastructure.entities.MovieReviewEntity;
import com.forthwall.cinema.movie.infrastructure.entities.MovieSessionEntity;
import com.forthwall.cinema.movie.service.MovieService;
import com.forthwall.cinema.movie.service.dto.MovieDescriptionDto;
import com.forthwall.cinema.movie.service.dto.MovieDto;
import com.forthwall.cinema.movie.service.dto.MovieTimeSessionDto;
import com.forthwall.cinema.movie.service.dto.ReviewDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service that provides movie access for update, storage and retrieval of the information
 * related to the movies.
 */
@Slf4j
@Service
public class MovieServiceImpl implements MovieService {
    private MovieSessionDao movieSessionDao;
    private MovieDao movieDao;
    private MovieReviewDao movieReviewDao;
    private ExternalWeb externalWeb;
    private ExternalMovieInformationDao externalMovieInformationDao;

    @Autowired
    public MovieServiceImpl(MovieSessionDao movieSessionDao, MovieDao movieDao, MovieReviewDao movieReviewDao,
        ExternalWeb externalWeb, ExternalMovieInformationDao externalMovieInformationDao) {
        this.movieSessionDao = movieSessionDao;
        this.movieDao = movieDao;
        this.movieReviewDao = movieReviewDao;
        this.externalWeb = externalWeb;
        this.externalMovieInformationDao = externalMovieInformationDao;
    }


    /**
     * @return returns all movies in a List that are in the DB
     */
    @Override
    public List<MovieDto> getAllMovies() {
        List<MovieEntity> moviesEntities = movieDao.findAll();
        ArrayList<MovieDto> movies = new ArrayList<>();
        for (MovieEntity entity : moviesEntities
        ) {
            MovieDto dto = new MovieDto();
            dto.setIdMovie(entity.getIdMovie());
            dto.setNameMovie(entity.getNameMovie());
            movies.add(dto);
        }
        return movies;
    }

    /**
     * @param date {@link LocalDate } date to be consulted
     * @return list of movies played for this date
     */
    @Override
    public List<MovieTimeSessionDto> getMoviesByDate(LocalDate date) {
        List<MovieSessionEntity> entities = movieSessionDao.findByDate(date);
        List<MovieTimeSessionDto> response = new ArrayList<>();
        for (MovieSessionEntity entity : entities
        ) {
            MovieTimeSessionDto dto = new MovieTimeSessionDto();
            dto.setIdMovie(entity.getMovie().getIdMovie());
            dto.setName(entity.getMovie().getNameMovie());
            dto.setRooms(entity.getRoom());
            dto.setTimeMovie(entity.getSessionTime());
            dto.setDateMovie(entity.getSessionDate());
            dto.setPrice(entity.getPrice());
            response.add(dto);
        }
        return response;
    }

    /**
     * Updates date of the Movie Session
     * @param timeSessionDto {@link MovieTimeSessionDto}
     */
    @Transactional
    @Override
    public void updateMovieTimeSession(MovieTimeSessionDto timeSessionDto) {
        log.debug("Calling movie session dao to find previously existing session");
        MovieSessionEntity sessionEntity = movieSessionDao.findById(timeSessionDto.getIdSession()).orElseThrow();
        log.debug("Calling movie session dao to update");
        movieSessionDao.updateDate(timeSessionDto.getDateMovie(), timeSessionDto.getTimeMovie().toLocalTime(),
            sessionEntity.getIdSession());
    }

    /**
     * Updates price of the movie session
     * @param timeSessionDto {@link MovieTimeSessionDto}
     */
    @Transactional
    @Override
    public void updateMoviePriceSession(MovieTimeSessionDto timeSessionDto) {
        MovieSessionEntity sessionEntity = movieSessionDao.findById(timeSessionDto.getIdSession()).orElseThrow();
        movieSessionDao.updatePriceById(timeSessionDto.getPrice(), sessionEntity.getIdSession());
    }

    /**
     * Saves the Review of the movie
     * @param dto {@link ReviewDto}
     */
    @Override
    public void saveReview(ReviewDto dto) {
        MovieReviewEntity entity = new MovieReviewEntity();
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setIdMovie(dto.getIdMovie());
        entity.setMovie(movieEntity);
        entity.setComment(dto.getComment());
        entity.setStars(dto.getStars());
        movieReviewDao.save(entity);
    }

    /**
     * Gets movie description provided by third party API. For the movie whose external movieId is stored in the DB
     * @param movieId internalMovie ID
     * @return {@link MovieDescriptionDto} provides detailed description of the movie given by third party API
     */
    @Override
    public MovieDescriptionDto getMovieDescriptionById(Long movieId) {
        ExternalMovieInformationEntity entity = externalMovieInformationDao.findByMovieId(movieId);
        return getMovieDescriptionByExternalId(entity.getIdExternal());
    }

    /**
     * Gets movie description provided by third party API. For the movie whose external movieId is not stored in the DB adn provide in the call
     * @param externalApiIdMovie  external API movie ID
     * @return @link MovieDescriptionDto} provides detailed description of the movie given by third party API
     */
    @Override
    public MovieDescriptionDto getMovieDescriptionByExternalId(String externalApiIdMovie) {
        return getMovieDescriptionFromExternal(externalApiIdMovie);
    }

    private MovieDescriptionDto getMovieDescriptionFromExternal(String idMovie) {
        ResponseIMDBDto dto = externalWeb.getDescriptionById(idMovie);
        MovieDescriptionDto movieDescriptionDto =  new MovieDescriptionDto();
        movieDescriptionDto.setName(dto.getName());
        movieDescriptionDto.setDescription(dto.getDescription());
        movieDescriptionDto.setReleaseDate(dto.getReleaseDate());
        movieDescriptionDto.setRuntime(dto.getRuntime());
        movieDescriptionDto.setIMDGRating(dto.getIMDGRating());
        ArrayList<MovieDescriptionDto.Rating> ratingList = new ArrayList();
        for (ResponseIMDBDto.Rating ratingDto: dto.getRating()) {
           MovieDescriptionDto.Rating rating =  new MovieDescriptionDto.Rating() ;
           rating.setSource(ratingDto.getSource());
           rating.setValue(ratingDto.getValue());
           ratingList.add(rating);

        }
        movieDescriptionDto.setRating(ratingList);
        return movieDescriptionDto;
    }
}
