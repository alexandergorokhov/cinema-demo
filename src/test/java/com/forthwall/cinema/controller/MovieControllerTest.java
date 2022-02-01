package com.forthwall.cinema.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.forthwall.cinema.movie.application.MovieController;
import com.forthwall.cinema.movie.application.view.request.ReviewRequest;
import com.forthwall.cinema.movie.service.TokeService;
import com.forthwall.cinema.movie.service.dto.MovieTimeSessionDto;
import com.forthwall.cinema.movie.service.dto.ReviewDto;
import com.forthwall.cinema.movie.service.implementation.MovieServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    MovieServiceImpl movieService;
    @MockBean
    TokeService tokeService;

    @Test
    public void movieSessionsByDate_shouldReturn200() throws Exception {
        MovieTimeSessionDto dto = getMovieTimeSessionDto();
        LocalDate date = LocalDate.of(2022, 01, 01);
        dto.setIdSession(1L);
        dto.setIdMovie(2L);
        dto.setPrice(new BigDecimal(1));
        dto.setDateMovie(date);
        dto.setName("Test movie");
        dto.setTimeMovie(LocalDateTime.now());
        List<MovieTimeSessionDto> expected = Arrays.asList(dto);
        BDDMockito.given(movieService.getMoviesByDate(date)).willReturn(expected);
        mockMvc.perform(get("/movieSession")
            .param("date", "2022-01-01"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].name", is(dto.getName())));
    }

    @Test
    public void movieSessionsByDate_shouldReturn500() throws Exception {
        MovieTimeSessionDto dto = getMovieTimeSessionDto();
        LocalDate date = LocalDate.of(2022, 01, 01);
        dto.setIdSession(1L);
        dto.setIdMovie(2L);
        dto.setPrice(new BigDecimal(1));
        dto.setDateMovie(date);
        dto.setName("Test movie");
        dto.setTimeMovie(LocalDateTime.now());
        List<MovieTimeSessionDto> expected = new ArrayList<>();
        BDDMockito.given(movieService.getMoviesByDate(date)).willThrow(new RuntimeException());
        mockMvc.perform(get("/movieSession")
            .param("date", "2022-01-01"))
            .andExpect(status().isInternalServerError());
    }

    @Test
    public void createReview_shouldReturn200() throws Exception {
        ReviewRequest request = new ReviewRequest();
        request.setIdMovie(1L);
        request.setComment("Good");
        request.setStars(2);
        ReviewDto reviewDto = Mockito.mock(ReviewDto.class);
        Mockito.doNothing().when(movieService).saveReview(reviewDto);
        String jsnoString = "{\"idMovie\":3, \"stars\":9, \"comment\":\"Nice\"}";
        mockMvc.perform(post("/review")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsnoString))
            .andExpect(status().isOk());
    }

    @Test
    public void createReview_shouldReturn400() throws Exception {
        ReviewRequest request = new ReviewRequest();
        request.setIdMovie(1L);
        request.setComment("Good");
        request.setStars(2);
        ReviewDto reviewDto = Mockito.mock(ReviewDto.class);
        Mockito.doNothing().when(movieService).saveReview(reviewDto);
        String jsnoString = "{\"idMovie\":3, \"stars\":12, \"comment\":\"Nice\"}";
        mockMvc.perform(post("/review")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsnoString))
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void createReview_shouldReturn500() throws Exception {
        String jsnoString = "{\"idMovie\":3, \"stars\":7, \"comment\":\"Nice\"}";
        doThrow(RuntimeException.class).when(movieService).saveReview(any(ReviewDto.class));

        mockMvc.perform(post("/review")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsnoString))
            .andExpect(status().isInternalServerError());
    }

    private MovieTimeSessionDto getMovieTimeSessionDto() {
        MovieTimeSessionDto dto = new MovieTimeSessionDto();
        LocalDate date = LocalDate.of(2022, 01, 01);
        dto.setIdSession(1L);
        dto.setIdMovie(2L);
        dto.setPrice(new BigDecimal(1));
        dto.setDateMovie(date);
        dto.setName("Test movie");
        dto.setTimeMovie(LocalDateTime.now());
        return dto;
    }
}
