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
@Table(name = "external_movie_description_storage")
@Getter
@Setter
public class ExternalMovieInformationEntity implements Serializable {
    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_external_description")
    private Long idExternalDescription;

    @ManyToOne
    @JoinColumn(name = "id_movie", nullable = false)
    private MovieEntity movie;

    @Column(name = "id_external")
    private String idExternal;

    @Column(name = "name_external_provider")
    private String nameExternalProvider;
}