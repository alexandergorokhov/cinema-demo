package com.forthwall.cinema.movie.infrastructure.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "movie_review")
@Getter
@Setter
public class MovieReviewEntity implements Serializable {
    private static final long serialVersionUID = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_review")
    private Long idReview;

    @ManyToOne
    @JoinColumn(name = "id_movie", nullable = false)
    private MovieEntity movie;

    @Column(name = "stars")
    private Integer stars;

    @Column(name = "comment")
    private String comment;

}
