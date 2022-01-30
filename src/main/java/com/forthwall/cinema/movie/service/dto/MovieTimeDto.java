package com.forthwall.cinema.movie.service.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A DTO used to transport information of movie timing
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class MovieTimeDto {
    /**
     * Id of the movie
     */
    private Long idMovie;
    /**
     * Name of the movie
     */
    private String name;

    /**
     * List of rooms where the movie is played
     */
    private ArrayList<String> rooms;

    /**
     * Date and time when the movie is played
     */
    private LocalDateTime dateTimeMovie;
}
