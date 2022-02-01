package com.forthwall.cinema.e2e;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.forthwall.cinema.movie.application.MovieController;
import com.forthwall.cinema.movie.infrastructure.MovieReviewDao;
import com.forthwall.cinema.movie.infrastructure.entities.MovieReviewEntity;
import com.forthwall.cinema.movie.service.TokeService;
import com.forthwall.cinema.movie.service.implementation.MovieServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest (properties = { "IMDB_API_KEY=x123","TOKEN=1234" })
public class MovieReviewE2ETest {

    private MockMvc mockMvc;

    @Autowired
    MovieController controller;

    @Autowired
    MovieServiceImpl movieService;

    @Autowired
    TokeService tokeService;

    @Autowired
    MovieReviewDao movieReviewDao;
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    @Test
    public void saveReview_test() throws Exception {
        String jsnoString = "{\"idMovie\":3, \"stars\":9, \"comment\":\"End to end test\"}";
        mockMvc.perform(post("/review")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsnoString))
            .andExpect(status().isOk());

        List<MovieReviewEntity> result = movieReviewDao.findAll();
        Assertions.assertEquals(result.size(), 6);
        Assertions.assertEquals(result.get(5).getMovie().getIdMovie(), 3);
        Assertions.assertEquals(result.get(5).getStars(), 9);
        Assertions.assertEquals(result.get(5).getComment(), "End to end test");

    }

}
