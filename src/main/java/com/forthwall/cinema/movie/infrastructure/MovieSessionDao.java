package com.forthwall.cinema.movie.infrastructure;

import com.forthwall.cinema.movie.infrastructure.entities.MovieSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface MovieSessionDao extends JpaRepository<MovieSessionEntity,Long> {

    String QUERY_BY_SESSION_DATE = "SELECT m_s.sessionDate, m_s.sessionTime, m_s.price, m_s.room"
        + "  FROM  MovieSessionEntity m_s, MovieEntity m_e  "
        + " where m_e.idMovie=m_s.movie and m_s.sessionDate =: date ";

    @Query(QUERY_BY_SESSION_DATE)
    List<MovieSessionEntity> findByDate(LocalDate date);
}
