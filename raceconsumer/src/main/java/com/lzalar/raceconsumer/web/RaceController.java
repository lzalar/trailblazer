package com.lzalar.raceconsumer.web;

import com.lzalar.raceconsumer.domain.RaceView;
import com.lzalar.raceconsumer.repository.RaceViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public void getSingleRace(@PathVariable String raceId) { // todo implement me

    }

    @GetMapping("/bla2")
    public void getAllRaceApplicationsForUser() { // todo implement me

    }

    @GetMapping("/{raceApplicationId}")
    public void getRaceApplication(@PathVariable String raceApplicationId) { // todo implement me

    }

}

