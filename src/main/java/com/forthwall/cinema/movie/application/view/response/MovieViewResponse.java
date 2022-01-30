package com.forthwall.cinema.movie.application.view.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Web response to see movie
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor

public class MovieViewResponse {
    /**
     * Id of the movie
     */
    private Long idMovie;
    /**
     * Name of the movie
     */
    private String name;
}
