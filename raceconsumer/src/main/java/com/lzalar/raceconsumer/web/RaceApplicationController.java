package com.lzalar.raceconsumer.web;

import com.lzalar.raceconsumer.web.dto.RaceApplicationPerUserDTO;
import com.lzalar.raceconsumer.domain.RaceApplicationView;
import com.lzalar.raceconsumer.service.RaceQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/race-application")
@RequiredArgsConstructor
public class RaceApplicationController {

    private final RaceQueryService raceQueryService;

    @GetMapping("/all/{userId}") // todo change
    public RaceApplicationPerUserDTO getAllRaceApplicationsForUser(@PathVariable UUID userId) {
        return raceQueryService.getAllRaceApplicationsForUser(userId);
    }

    @GetMapping("{raceApplicationId}")
    public RaceApplicationView getRaceApplication(@PathVariable UUID raceApplicationId) {
        return raceQueryService.getRaceApplication(raceApplicationId);
    }

}

