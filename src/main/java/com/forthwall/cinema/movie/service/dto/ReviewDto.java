package com.forthwall.cinema.movie.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO object for movie review
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ReviewDto {
    /**
     * Id of the review
     */
    private Long idReview;

    /**
     * Id of the movie
     */
    private Long idMovie;

    /**
     * Stars of the movie
     */
    private Integer stars;

    /**
     * Comment of the review
     */
    private String comment;
}
