package br.com.movie.awards.adapter.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AwardsIntervalResponse {

    private List<IntervalDto> min;
    private List<IntervalDto> max;

}
