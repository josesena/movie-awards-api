package br.com.movie.awards.domain.service;

import br.com.movie.awards.adapter.rest.dto.AwardsIntervalResponse;
import br.com.movie.awards.adapter.rest.dto.IntervalDTO;
import br.com.movie.awards.infrastructure.csv.dto.MovieDTO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CalculatorService {

    public List<IntervalDTO> calculateIntervals(List<MovieDTO> winners) {
        var producerWins = winners.stream()
                .flatMap(movie -> Arrays.stream(movie.getProducers().split("(,| and )"))
                        .map(String::trim)
                        .map(producer -> Map.entry(producer, movie.getYear())))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));

        return producerWins.entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .flatMap(entry -> {
                    var sortedYears = entry.getValue().stream().sorted().toList();
                    var producer = entry.getKey();

                    return IntStream.range(1, sortedYears.size())
                            .mapToObj(i -> IntervalDTO.builder()
                                    .producer(producer)
                                    .interval(sortedYears.get(i) - sortedYears.get(i - 1))
                                    .previousWin(sortedYears.get(i - 1))
                                    .followingWin(sortedYears.get(i))
                                    .build());
                })
                .toList();
    }

    public AwardsIntervalResponse toResponse(List<IntervalDTO> intervals) {
        var min = intervals.stream().mapToInt(IntervalDTO::getInterval).min().orElse(0);
        var max = intervals.stream().mapToInt(IntervalDTO::getInterval).max().orElse(0);

        var minList = intervals.stream().filter(i -> i.getInterval() == min).toList();
        var maxList = intervals.stream().filter(i -> i.getInterval() == max).toList();

        return AwardsIntervalResponse.builder()
                .min(minList)
                .max(maxList)
                .build();
    }
}
