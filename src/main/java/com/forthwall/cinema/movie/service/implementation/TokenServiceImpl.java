package com.forthwall.cinema.movie.service.implementation;

import com.forthwall.cinema.movie.service.TokeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public final class TokenServiceImpl implements TokeService {

    @Value("${token}")
    private   String TOKEN ;

    @Override
    public String getToken() {
        return TOKEN;
    }
}
