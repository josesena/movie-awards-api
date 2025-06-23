package br.com.movie.awards.usecase.impl;

import br.com.movie.awards.adapter.rest.dto.AwardsIntervalResponse;
import br.com.movie.awards.domain.service.CalculatorService;
import br.com.movie.awards.infrastructure.mapper.MovieMapper;
import br.com.movie.awards.infrastructure.repository.MovieRepository;
import br.com.movie.awards.usecase.CalculeIntervalUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CalculeIntervalUseCaseImpl implements CalculeIntervalUseCase {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final CalculatorService calculatorService;
    @Override
    public AwardsIntervalResponse execute() {

        var winners = movieRepository.findByWinnerTrue()
                .stream()
                .map(movieMapper::toDto)
                .collect(Collectors.toList());

       var intervals = calculatorService.calculateIntervals(winners);

        return calculatorService.toResponse(intervals);
    }
}
