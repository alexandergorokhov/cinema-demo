package com.forthwall.cinema.movie.application.view.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Web request to see movie timing
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ReviewRequest {
    /**
     * Id of the movie
     */
    private Long idMovie;

    /**
     * Rated starts
     */
    private Integer stars;

    /**
     * Comment that be left
     */
    private String comment;
}
