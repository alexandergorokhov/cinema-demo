package com.forthwall.cinema.movie.infrastructure.entities;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity class to be used for Movie Session storage
 */
@Entity
@Table(name = "movie_session")
@Getter
@Setter
public class MovieSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_session")
    private Long idSession;

    @ManyToOne
    @JoinColumn(name = "id_movie", nullable = false)
    private MovieEntity movie;
    @Column(name = "session_time")
    private LocalDateTime sessionTime;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "room")
    private String room;
    @Column(name = "session_date")
    private LocalDate sessionDate;
}
