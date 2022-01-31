package com.forthwall.cinema.movie.application.view.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * Web response to see movie timing
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MovieTimeSessionViewResponse {

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
    private LocalDate dateMovie;

    /**
     * Time when the movie is played
     */
    private String timeMovie;

    /**
     * Price of the Session
     */
    private BigDecimal price;
}
