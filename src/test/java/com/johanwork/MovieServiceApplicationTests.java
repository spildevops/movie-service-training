package com.johanwork;

import com.johanwork.domain.Genre;
import com.johanwork.dto.MovieDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieServiceApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void health(){
		ResponseEntity<String> response =
				this.restTemplate.getForEntity("/actuator/health", String.class);

		Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	void allMovies() {
		List<MovieDto> movies = getMovies("/api/movies");
		Assertions.assertEquals(6, movies.size());
	}

	@Test
	void allMoviesByGenre() {
		List<MovieDto> movies = getMovies("/api/movies/ACTION");
		Assertions.assertEquals(3, movies.size());
		Assertions.assertTrue(movies.stream()
				.map(MovieDto::genre)
				.allMatch(Genre.ACTION::equals));
	}

	private List<MovieDto> getMovies(String uri){
		RequestEntity<Object> requestEntity =
				new RequestEntity<>(HttpMethod.GET, URI.create(uri));

		ResponseEntity<List<MovieDto>> response = this.restTemplate.exchange(
				requestEntity,
				new ParameterizedTypeReference<List<MovieDto>>() {
				});
		log.info("Response: {}", response.getBody());
		Assertions.assertNotNull(response.getBody());
		return response.getBody();
	}
}
