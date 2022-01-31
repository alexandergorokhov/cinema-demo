package com.forthwall.cinema.movie.service.implementation;

import com.forthwall.cinema.movie.infrastructure.MovieDao;
import com.forthwall.cinema.movie.infrastructure.MovieReviewDao;
import com.forthwall.cinema.movie.infrastructure.MovieSessionDao;
import com.forthwall.cinema.movie.infrastructure.api.ExternalWeb;
import com.forthwall.cinema.movie.infrastructure.api.dto.ResponseIMDBDto;
import com.forthwall.cinema.movie.infrastructure.entities.MovieEntity;
import com.forthwall.cinema.movie.infrastructure.entities.MovieReviewEntity;
import com.forthwall.cinema.movie.infrastructure.entities.MovieSessionEntity;
import com.forthwall.cinema.movie.service.MovieService;
import com.forthwall.cinema.movie.service.dto.MovieDescriptionDto;
import com.forthwall.cinema.movie.service.dto.MovieDto;
import com.forthwall.cinema.movie.service.dto.MovieTimeSessionDto;
import com.forthwall.cinema.movie.service.dto.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MovieServiceImpl implements MovieService {
    private MovieSessionDao movieSessionDao;
    private MovieDao movieDao;
    private MovieReviewDao movieReviewDao;
    private ExternalWeb externalWeb;

    @Autowired
    public MovieServiceImpl(MovieSessionDao movieSessionDao, MovieDao movieDao, MovieReviewDao movieReviewDao,
        ExternalWeb externalWeb) {
        this.movieSessionDao = movieSessionDao;
        this.movieDao = movieDao;
        this.movieReviewDao = movieReviewDao;
        this.externalWeb = externalWeb;
    }

    @Override
    public MovieTimeSessionDto getMovieByIdAndDate(MovieTimeSessionDto movieTimeSessionDto) {
        //movieSessionDao
        return null;
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
            response.add(dto);
        }
        return response;
    }

    @Transactional
    @Override
    public void updateMovieTimeSession(MovieTimeSessionDto timeSessionDto) {
        MovieSessionEntity sessionEntity = movieSessionDao.findById(timeSessionDto.getIdSession()).orElseThrow();
        movieSessionDao.updateDate(timeSessionDto.getDateMovie(), timeSessionDto.getTimeMovie().toLocalTime(),
            sessionEntity.getIdSession());
    }

    @Transactional
    @Override
    public void updateMoviePriceSession(MovieTimeSessionDto timeSessionDto) {
        MovieSessionEntity sessionEntity = movieSessionDao.findById(timeSessionDto.getIdSession()).orElseThrow();
        movieSessionDao.updatePriceById(timeSessionDto.getPrice(), sessionEntity.getIdSession());
    }

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

    @Override
    public MovieDescriptionDto getMovieDescription(Long movieId) {
        // call local DB to check if the movie is there
        return null;
    }

    @Override
    public MovieDescriptionDto getMovieDescriptionByExternalId(String idMovie) {
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
