package com.forthwall.cinema.movie.application.view.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Web response for movie description
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MovieDescriptionResponse {
    /**
     * Name of the movie
     */
    private String name;
    /**
     * Description of the movie
     */
    private String description;
    /**
     * Release date
     */
    private String releaseDate;
    /**
     * List of {@link Rating}
     */
    private List<Rating> rating;
    /**
     * IMDG rating
     */
    private String IMDGRating;
    /**
     * Runtime of the movie
     */
    private String runtime;

    /**
     * Inner class that describes third party ratings
     */
    @Setter
    @Getter
    public static class Rating {

        /**
         * Source of rating
         */
        private String source;

        /**
         * Value rating
         */
        private String value;
    }
}
