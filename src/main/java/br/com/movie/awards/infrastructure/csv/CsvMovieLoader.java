package br.com.movie.awards.infrastructure.csv;

import br.com.movie.awards.domain.dto.MovieDTO;
import br.com.movie.awards.infrastructure.mapper.MovieMapper;
import br.com.movie.awards.infrastructure.repository.MovieRepository;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Component
public class CsvMovieLoader implements CommandLineRunner {

    private MovieRepository movieRepository;
    private MovieMapper movieMapper;

    @Override
    public void run(String... args) {
        log.info("Iniciando a importacao do arquivo CSV ...");
        try (CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(getClass().getResourceAsStream("/movielist.csv"), StandardCharsets.UTF_8))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            List<String[]> lines = reader.readAll();
            var listMovieDto = lines.stream()
                            .skip(1)
                                    .map(line -> MovieDTO.builder()
                                            .year(Integer.parseInt(line[0].trim()))
                                            .title(line[1].trim())
                                            .studios(line[2].trim())
                                            .producers(line[3].trim())
                                            .winner("yes".equalsIgnoreCase(line[4].trim())).build())
                    .collect(Collectors.toList());

            movieRepository.saveAll(movieMapper.toEntity(listMovieDto));

        } catch (Exception e) {
            log.error("Ocorreu um erro ao processar arquivo CSV", e);
        }
    }
}
