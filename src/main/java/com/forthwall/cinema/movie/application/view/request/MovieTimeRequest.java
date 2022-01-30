package com.forthwall.cinema.movie.application.view.request;

import lombok.Builder;
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
public class MovieTimeRequest {
    /**
     * Id of the movie
     */
    private Long idMovie;
    /**
     * Date for which the availability of the movie wants to be queried.
     */
    private String date;
}
