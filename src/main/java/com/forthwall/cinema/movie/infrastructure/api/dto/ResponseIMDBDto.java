package com.forthwall.cinema.movie.infrastructure.api.dto;

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
 * DTO used for communication with IMDBApplicaiton
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ResponseIMDBDto {
    /**
     * Name of the movie
     */
    @JsonProperty("Title")
    private String name;
    /**
     * Description of the movie
     */
    @JsonProperty("Plot")
    private String description;
    /**
     * Release date
     */
    @JsonProperty("Released")
    private String releaseDate;
    /**
     * Rating
     */
    @JsonProperty("Ratings")
    private List<Rating> rating;
    /**
     * IMDG rating
     */
    @JsonProperty("imdbRating")
    private String IMDGRating;
    /**
     * Runtime of the movie
     */
    @JsonProperty("Runtime")
    private String runtime;

    @Setter
    @Getter
    public static class Rating {
        @JsonProperty("Source")
        private String source;
        @JsonProperty("Value")
        private String value;
    }

}
