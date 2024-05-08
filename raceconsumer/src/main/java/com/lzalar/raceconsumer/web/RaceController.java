package com.lzalar.raceconsumer.web;

import com.lzalar.raceconsumer.domain.RaceViewProjection;
import com.lzalar.raceconsumer.service.RaceQueryService;
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

    private final RaceQueryService raceQueryService;

    @GetMapping
    public List<RaceViewProjection> getAllRaces() {
        return raceQueryService.getAllRaces();
    }

    @GetMapping("/{raceId}")
    public RaceViewProjection getSingleRace(@PathVariable UUID raceId) {
        return raceQueryService.getRace(raceId);
    }
}

