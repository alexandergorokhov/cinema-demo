package com.forthwall.cinema.movie.infrastructure;

import com.forthwall.cinema.movie.infrastructure.entities.ExternalMovieInformationEntity;
import com.forthwall.cinema.movie.infrastructure.entities.MovieEntity;
import com.forthwall.cinema.movie.infrastructure.entities.MovieSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExternalMovieInformationDao extends JpaRepository<ExternalMovieInformationEntity,Long> {

    String QUERY_BY_SESSION_DATE = "SELECT e_m_d_s.id_external_description, e_m_d_s.id_movie,"
        + "e_m_d_s.id_external , e_m_d_s.name_external_provider"
        + "  FROM  external_movie_description_storage e_m_d_s "
        + " where e_m_d_s.id_movie= :movieId ";

    @Query(value = QUERY_BY_SESSION_DATE, nativeQuery = true)
    ExternalMovieInformationEntity findByMovieId(@Param("movieId") Long movieId);
}
