package com.forthwall.cinema.infrastructure;

import com.forthwall.cinema.movie.infrastructure.MovieSessionDao;
import com.forthwall.cinema.movie.infrastructure.entities.MovieSessionEntity;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MovieSessionDaoTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    MovieSessionDao movieSessionDao;

    @Test
    public void findBySessionBySessionDate_test() {
        LocalDate testDate = LocalDate.of(2022, 9, 9);
        List<MovieSessionEntity> result = movieSessionDao.findByDate(testDate);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(testDate, result.get(0).getSessionDate());

    }

    @Test
    public void updatePriceBySessionId_test() {
        BigDecimal price = new BigDecimal(100.00).setScale(2);
        movieSessionDao.updatePriceById(price,1L);
        MovieSessionEntity result = movieSessionDao.findById(1L).get();
        Assertions.assertEquals(price, result.getPrice());
    }
}
