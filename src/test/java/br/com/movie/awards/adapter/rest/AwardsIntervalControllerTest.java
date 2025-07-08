package br.com.movie.awards.adapter.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
		"csv.file=classpath:movielist-test.csv"
})
class MovieAwardsApiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("Deve retornar os produtores com maior e menor intervalo entre prêmios")
	void shouldGetProducersMinMaxIntervals() throws Exception {
		mockMvc.perform(get("/awards/intervals"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.min", hasSize(greaterThanOrEqualTo(1))))
				.andExpect(jsonPath("$.min[?(@.producer == 'Joel Silver')].interval", contains(1)))
				.andExpect(jsonPath("$.min[?(@.producer == 'Joel Silver')].previousWin", contains(1990)))
				.andExpect(jsonPath("$.min[?(@.producer == 'Joel Silver')].followingWin", contains(1991)))
				.andExpect(jsonPath("$.max", hasSize(greaterThanOrEqualTo(1))))
				.andExpect(jsonPath("$.max[?(@.producer == 'Matthew Vaughn')].interval", contains(13)))
				.andExpect(jsonPath("$.max[?(@.producer == 'Matthew Vaughn')].previousWin", contains(2002)))
				.andExpect(jsonPath("$.max[?(@.producer == 'Matthew Vaughn')].followingWin", contains(2015)));
	}
	@Test
	@DisplayName("Deve validar resposta exata baseada no arquivo movielist-test.csv")
	void shouldMatchExactJsonOutputFromCsv() throws Exception {
		String expectedJson = StreamUtils.copyToString(
				new ClassPathResource("movie-expected.json").getInputStream(),
				StandardCharsets.UTF_8
		);

		mockMvc.perform(get("/awards/intervals"))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedJson, true));
	}

}
