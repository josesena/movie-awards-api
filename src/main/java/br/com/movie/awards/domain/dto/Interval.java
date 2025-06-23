package br.com.movie.awards.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Interval {

    private String producer;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;

}
