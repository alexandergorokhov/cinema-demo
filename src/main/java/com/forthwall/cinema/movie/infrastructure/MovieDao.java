package com.forthwall.cinema.movie.infrastructure;

import com.forthwall.cinema.movie.infrastructure.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieDao extends JpaRepository<MovieEntity,Long> {

}
