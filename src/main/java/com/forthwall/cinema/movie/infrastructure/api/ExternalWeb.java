package com.forthwall.cinema.movie.infrastructure.api;

import com.forthwall.cinema.movie.infrastructure.api.dto.ResponseIMDBDto;

public interface ExternalWeb {
    ResponseIMDBDto getDescriptionById(String id);
}
