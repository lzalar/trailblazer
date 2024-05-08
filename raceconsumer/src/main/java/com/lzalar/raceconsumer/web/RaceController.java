package com.lzalar.raceconsumer.web;

import com.lzalar.raceconsumer.domain.RaceView;
import com.lzalar.raceconsumer.service.RaceQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/race")
@RequiredArgsConstructor
public class RaceController {

    private final RaceQueryService raceQueryService;

    @GetMapping
    public Page<RaceView> getAllRaces(Pageable pageable) {
        return raceQueryService.getAllRaces(pageable);
    }

    @GetMapping("/{raceId}")
    public RaceView getSingleRace(@PathVariable UUID raceId) {
        return raceQueryService.getRace(raceId);
    }
}

