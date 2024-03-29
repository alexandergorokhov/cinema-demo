package com.forthwall.cinema.movie.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A DTO used to transport information of movie timing
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class MovieTimeSessionDto {
    /**
     * Id of the movie
     */
    private Long idMovie;

    /**
     * Id of the session
     */
    private Long idSession;
    /**
     * Name of the movie
     */
    private String name;

    /**
     * Rooms where the movie is played
     */
    private String rooms;

    /**
     * Date and time when the movie is played
     */
    private LocalDateTime timeMovie;

    /**
     * Date  when the movie is played
     */
    private LocalDate dateMovie;

    /**
     * Price of the movie whic is played
     */
    private BigDecimal price;
}
