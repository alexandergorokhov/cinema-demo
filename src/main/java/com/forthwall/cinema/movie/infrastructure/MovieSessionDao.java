package com.forthwall.cinema.movie.infrastructure;

import com.forthwall.cinema.movie.infrastructure.entities.MovieSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MovieSessionDao extends JpaRepository<MovieSessionEntity,Long> {

    String QUERY_BY_SESSION_DATE = "SELECT m_s.id_session, m_e.id_movie,m_s.session_date, m_s.session_time, m_s.price, m_s.room"
        + "  FROM  movie_session m_s, movies m_e  "
        + " where m_e.id_movie=m_s.id_movie and m_s.session_date =:date ";

    @Query(value = QUERY_BY_SESSION_DATE, nativeQuery = true)
    List<MovieSessionEntity> findByDate(@Param("date") LocalDate date);
}
