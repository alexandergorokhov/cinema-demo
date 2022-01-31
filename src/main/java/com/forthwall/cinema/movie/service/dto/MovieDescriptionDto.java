package com.forthwall.cinema.movie.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * DTO for movie description on a service level.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MovieDescriptionDto {
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
     * Rating
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

    @Setter
    @Getter
    public static class Rating {

        private String source;

        private String value;
    }
}
