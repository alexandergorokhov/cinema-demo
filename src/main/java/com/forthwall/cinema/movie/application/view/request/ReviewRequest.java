package com.forthwall.cinema.movie.application.view.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

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
    @NonNull
    private Long idMovie;

    /**
     * Rated starts
     */
    @Min(value = 0, message = "Minimum rating is 0")
    @Max(value = 10, message = "Maximum rating is 10")
    private Integer stars;

    /**
     * Comment that be left
     */
    @Size(max = 200, message
        = "Comments should be less than 200 characters")
    private String comment;
}
