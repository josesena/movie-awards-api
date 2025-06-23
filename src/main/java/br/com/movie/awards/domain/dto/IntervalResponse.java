package br.com.movie.awards.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class IntervalResponse {

    private List<Interval> min;
    private List<Interval> max;

}
