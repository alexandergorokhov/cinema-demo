package com.forthwall.cinema.movie.infrastructure;

import com.forthwall.cinema.movie.infrastructure.entities.MovieSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MovieSessionDao extends JpaRepository<MovieSessionEntity, Long> {

    String QUERY_BY_SESSION_DATE = "SELECT m_s.id_session, m_e.id_movie,m_s.session_date, m_s.session_time, m_s.price, m_s.room"
        + "  FROM  movie_session m_s, movies m_e  "
        + " where m_e.id_movie=m_s.id_movie and m_s.session_date =:date ";

    String UPDATE_PRICE = "UPDATE movie_session m_s SET m_s.price = :price"
        + " where m_s.id_session =:id_session ";

    String UPDATE_TIME = "UPDATE  movie_session m_s SET m_s.session_time = :session_time,"
        + " m_s.session_date = :session_date "
        + " where m_s.id_session =:id_session ";

    @Query(value = QUERY_BY_SESSION_DATE, nativeQuery = true)
    List<MovieSessionEntity> findByDate(@Param("date") LocalDate date);

    @Modifying
    @Query(value = UPDATE_TIME, nativeQuery = true)
    void updateDate(@Param("session_date") LocalDate date,
        @Param("session_time") LocalTime time, @Param("id_session") Long idSession);

    @Modifying
    @Query(value = UPDATE_PRICE, nativeQuery = true)
    void updatePriceById(@Param("price") BigDecimal price,
        @Param("id_session") Long idSession);
}
