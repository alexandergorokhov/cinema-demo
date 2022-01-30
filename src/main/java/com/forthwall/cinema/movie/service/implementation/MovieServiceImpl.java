package com.forthwall.cinema.movie.service.implementation;

import com.forthwall.cinema.movie.infrastructure.MovieDao;
import com.forthwall.cinema.movie.infrastructure.MovieSessionDao;
import com.forthwall.cinema.movie.infrastructure.entities.MovieEntity;
import com.forthwall.cinema.movie.service.MovieService;
import com.forthwall.cinema.movie.service.dto.MovieDto;
import com.forthwall.cinema.movie.service.dto.MovieTimeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public MovieTimeDto getMovieByIdAndDate(MovieTimeDto movieTimeDto) {
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
        for (MovieEntity entity: moviesEntities
        ) {
            MovieDto dto = new MovieDto();
            dto.setIdMovie(entity.getIdMovie());
            dto.setNameMovie(entity.getNameMovie());
            movies.add(dto);
        }
       return movies;
    }
}
