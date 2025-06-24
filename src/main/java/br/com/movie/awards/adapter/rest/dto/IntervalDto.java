package br.com.movie.awards.adapter.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class IntervalDto {

    private String producer;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;

}
