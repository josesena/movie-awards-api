package br.com.movie.awards.infrastructure.mapper;

import br.com.movie.awards.domain.model.Movie;
import br.com.movie.awards.infrastructure.csv.dto.MovieDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(source = "year", target = "movieYear")
    Movie toEntity(MovieDTO dto);
    @Mapping(source = "year", target = "movieYear")
    List<Movie> toEntity(List<MovieDTO> dto);
    @Mapping(source = "movieYear", target = "year")
    MovieDTO toDto(Movie entity);
}
