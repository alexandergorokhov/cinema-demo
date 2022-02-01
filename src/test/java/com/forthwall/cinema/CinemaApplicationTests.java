package com.forthwall.cinema;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest (properties = { "IMDB_API_KEY=x123","token=1234" })
class CinemaApplicationTests {

	@Test
	void contextLoads() {
	}

}
