package com.forthwall.cinema.service;

import com.forthwall.cinema.movie.infrastructure.ExternalMovieInformationDao;
import com.forthwall.cinema.movie.infrastructure.MovieDao;
import com.forthwall.cinema.movie.infrastructure.MovieReviewDao;
import com.forthwall.cinema.movie.infrastructure.MovieSessionDao;
import com.forthwall.cinema.movie.infrastructure.api.ExternalWeb;
import com.forthwall.cinema.movie.infrastructure.entities.MovieEntity;
import com.forthwall.cinema.movie.service.MovieService;
import com.forthwall.cinema.movie.service.dto.MovieDto;
import com.forthwall.cinema.movie.service.implementation.MovieServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieServiceImpl.class)
public class MovieServiceTest {

    @Autowired
    MovieServiceImpl movieService;

    @MockBean
    MovieDao movieDao;
    @MockBean
    MovieSessionDao movieSessionDao;
    @MockBean
    MovieReviewDao movieReviewDao;
    @MockBean
    ExternalWeb externalWeb;
    @MockBean
    ExternalMovieInformationDao externalMovieInformationDao;

    @Test
    public void getAllMovies_test(){
        MovieEntity movie1 = new MovieEntity();
        movie1.setIdMovie(1L);
        movie1.setNameMovie("Harry");
        MovieEntity movie2 = new MovieEntity();
        movie2.setIdMovie(2L);
        movie2.setNameMovie("Lassie");
        BDDMockito.given(movieDao.findAll()).willReturn(Arrays.asList(movie1,movie2));
        List<MovieDto> result = movieService.getAllMovies();
        Mockito.verify(movieDao,Mockito.times(1)).findAll();
        Assertions.assertEquals(result.size(), 2);
        Assertions.assertEquals(result.get(0).getIdMovie(), movie1.getIdMovie());
        Assertions.assertEquals(result.get(1).getIdMovie(), movie2.getIdMovie());



    }
}
