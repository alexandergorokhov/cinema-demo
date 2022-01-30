package com.forthwall.cinema.movie.service.implementation;

import com.forthwall.cinema.movie.infrastructure.MovieDao;
import com.forthwall.cinema.movie.infrastructure.MovieSessionDao;
import com.forthwall.cinema.movie.infrastructure.entities.MovieEntity;
import com.forthwall.cinema.movie.infrastructure.entities.MovieSessionEntity;
import com.forthwall.cinema.movie.service.MovieService;
import com.forthwall.cinema.movie.service.dto.MovieDto;
import com.forthwall.cinema.movie.service.dto.MovieTimeSessionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private MovieSessionDao movieSessionDao;
    private MovieDao movieDao;

    @Autowired
    public MovieServiceImpl(MovieSessionDao movieSessionDao, MovieDao movieDao) {
        this.movieSessionDao = movieSessionDao;
        this.movieDao = movieDao;
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
        for (MovieSessionEntity entity: entities
        ) {
            MovieTimeSessionDto dto = new MovieTimeSessionDto();
            dto.setName(entity.getMovie().getNameMovie());
            dto.setRooms(entity.getRoom());
            dto.setTimeMovie(entity.getSessionTime());
            response.add(dto);
        }
        return response;
    }
}
