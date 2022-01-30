package com.forthwall.cinema.movie.application.view.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
     * Id of the movie
     */
    private Long idMovie;
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
}
