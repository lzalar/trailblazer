package com.lzalar.raceconsumer.web;

import com.lzalar.raceconsumer.domain.RaceView;
import com.lzalar.raceconsumer.repository.RaceViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/race")
@RequiredArgsConstructor
public class RaceController {

    private final RaceViewRepository raceViewRepository;

    @GetMapping
    public List<RaceView> getAllRaces() {
        return raceViewRepository.findAll();
    }

    @GetMapping("/{raceId}")
    public RaceView getSingleRace(@PathVariable UUID raceId) { // todo implement me
        return raceViewRepository.findById(raceId).get();
    }
}

