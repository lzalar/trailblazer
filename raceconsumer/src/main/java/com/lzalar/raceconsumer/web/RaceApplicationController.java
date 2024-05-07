package com.lzalar.raceconsumer.web;

import com.lzalar.raceconsumer.domain.RaceApplicationPerUser;
import com.lzalar.raceconsumer.repository.RaceApplicationPerUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/race-application")
@RequiredArgsConstructor
public class RaceApplicationController {

    private final RaceApplicationPerUserRepository raceApplicationPerUserRepository;

    @GetMapping("/all/{userId}") // todo change
    public List<RaceApplicationPerUser> getAllRaceApplicationsForUser(@PathVariable UUID userId) {
        return raceApplicationPerUserRepository.findAll();
    }

    @GetMapping("{raceApplicationId}")
    public void getRaceApplication(@PathVariable String raceApplicationId) { // todo implement me

    }

}

