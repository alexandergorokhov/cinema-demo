package com.forthwall.cinema.movie.infrastructure.api;

import com.forthwall.cinema.movie.infrastructure.api.dto.ResponseIMDBDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.HttpURLConnection;

@Service
public class IMDBApplication implements ExternalWeb {

    RestTemplate restTemplate;
    private static final String IMDB_URL = "http://www.omdbapi.com/";

    @Autowired
    public IMDBApplication(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public ResponseIMDBDto getDescriptionById(String id) {
        HttpURLConnection con = null;
        try {
            String urlTemplate = UriComponentsBuilder.fromHttpUrl(IMDB_URL)
                .queryParam("apiKey", "e4f33820")
                .queryParam("i", id)
                .encode()
                .toUriString();

            ResponseEntity<ResponseIMDBDto> r
                = restTemplate.getForEntity(urlTemplate, ResponseIMDBDto.class);

            return r.getBody();
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }

    }
}
