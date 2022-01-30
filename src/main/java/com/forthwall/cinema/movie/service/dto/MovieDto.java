package com.forthwall.cinema.movie.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * A DTO used to transport information of the movie
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class MovieDto {
    /**
     * Id of the movie dto
     */
    private Long idMovie;

    /**
     * name of the movie
     */
    private String nameMovie;
}
