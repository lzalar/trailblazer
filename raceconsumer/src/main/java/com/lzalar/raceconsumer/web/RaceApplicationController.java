package com.lzalar.raceconsumer.web;

import com.lzalar.raceconsumer.domain.AppliedRacesPerUser;
import com.lzalar.raceconsumer.domain.RaceView;
import com.lzalar.raceconsumer.repository.AppliedRacePerUserRepository;
import com.lzalar.raceconsumer.repository.RaceViewRepository;
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

    private final AppliedRacePerUserRepository appliedRacePerUserRepository;

    @GetMapping("/all/{userId}") // todo change
    public List<AppliedRacesPerUser> getAllRaceApplicationsForUser(@PathVariable UUID userId) {
        return appliedRacePerUserRepository.findAll();
    }

    @GetMapping("{raceApplicationId}")
    public void getRaceApplication(@PathVariable String raceApplicationId) { // todo implement me

    }

}

