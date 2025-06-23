package br.com.movie.awards.usecase.impl;

import br.com.movie.awards.domain.dto.Interval;
import br.com.movie.awards.domain.dto.IntervalResponse;
import br.com.movie.awards.infrastructure.mapper.MovieMapper;
import br.com.movie.awards.infrastructure.repository.MovieRepository;
import br.com.movie.awards.usecase.CalculeIntervalUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class CalculeIntervalUseCaseImpl implements CalculeIntervalUseCase {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    @Override
    public IntervalResponse execute() {

        var winners = movieRepository.findByWinnerTrue()
                .stream()
                .map(movieMapper::toDto)
                .collect(Collectors.toList());

        var producerWins = winners.stream()
                .flatMap(movie -> Arrays.stream(movie.getProducers().split("(,| and )"))
                        .map(String::trim)
                        .map(producer -> Map.entry(producer, movie.getYear())))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));

        var intervals = producerWins.entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .flatMap(entry -> {
                    var sortedYears = entry.getValue().stream().sorted().toList();
                    var producer = entry.getKey();

                    return IntStream.range(1, sortedYears.size())
                            .mapToObj(i -> Interval.builder()
                                    .producer(producer)
                                    .interval(sortedYears.get(i) - sortedYears.get(i - 1))
                                    .previousWin(sortedYears.get(i - 1))
                                    .followingWin(sortedYears.get(i))
                                    .build());
                })
                .toList();
        var min = intervals.stream().mapToInt(Interval::getInterval).min().orElse(0);
        var max = intervals.stream().mapToInt(Interval::getInterval).max().orElse(0);

        var minList = intervals.stream().filter(i -> i.getInterval() == min).toList();
        var maxList = intervals.stream().filter(i -> i.getInterval() == max).toList();

        return IntervalResponse.builder()
                .min(minList)
                .max(maxList)
                .build();
    }
}
