package br.com.movie.awards.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MovieDTO {

    private Long id;
    private Integer year;
    private String title;
    private String studios;
    private String producers;
    private boolean winner;
}
