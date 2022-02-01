package com.forthwall.cinema.infrastructure;

import com.forthwall.cinema.movie.infrastructure.MovieReviewDao;
import com.forthwall.cinema.movie.infrastructure.entities.MovieEntity;
import com.forthwall.cinema.movie.infrastructure.entities.MovieReviewEntity;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJpaTest
public class MovieReviewDaoTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    MovieReviewDao dao;

    @Test
    public void saveTest() {
        MovieReviewEntity entity = new MovieReviewEntity();
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setIdMovie(1L);
        movieEntity.setNameMovie("Napoleon");
        entity.setStars(4);
        entity.setComment("Nice");
        entity.setMovie(movieEntity);
        dao.save(entity);
        MovieReviewEntity result = dao.findById(6L).get();
        Assertions.assertEquals(entity,result);

    }
}
