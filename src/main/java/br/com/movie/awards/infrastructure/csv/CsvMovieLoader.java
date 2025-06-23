package br.com.movie.awards.infrastructure.csv;

import br.com.movie.awards.infrastructure.csv.dto.MovieDTO;
import br.com.movie.awards.infrastructure.mapper.MovieMapper;
import br.com.movie.awards.infrastructure.repository.MovieRepository;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CsvMovieLoader implements CommandLineRunner {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieMapper movieMapper;
    @Value("${csv.file:classpath:movielist.csv}")
    private Resource csvFile;

    @Override
    public void run(String... args) {
        log.info("Iniciando a importacao do arquivo CSV ...");
        try (CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(csvFile.getInputStream(), StandardCharsets.UTF_8))
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
            log.info("Dados salvo no banco H2");
        } catch (Exception e) {
            log.error("Ocorreu um erro ao processar arquivo CSV", e);
        }
    }
}
