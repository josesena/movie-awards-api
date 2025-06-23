package br.com.movie.awards.adapter.rest;


import br.com.movie.awards.adapter.rest.dto.AwardsIntervalResponse;
import br.com.movie.awards.usecase.CalculeIntervalUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("awards")
public class AwardsIntervalController {

    private final CalculeIntervalUseCase calculeIntervalUseCase;

    @GetMapping(path = "/intervals", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AwardsIntervalResponse> getInterval(){
        return ResponseEntity.ok(calculeIntervalUseCase.execute());
    }
}
