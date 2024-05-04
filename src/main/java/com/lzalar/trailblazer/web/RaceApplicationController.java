package com.lzalar.trailblazer.web;

import com.lzalar.trailblazer.domain.race.Race;
import com.lzalar.trailblazer.domain.race.RaceApplication;
import com.lzalar.trailblazer.service.RaceService;
import com.lzalar.trailblazer.web.dto.RaceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/application")
@RequiredArgsConstructor
public class RaceApplicationController {

    private final RaceService raceService;

    @GetMapping
    public List<RaceApplication> getRaceApplications(){ // todo implement me
        return List.of();
    }

    @GetMapping("/{raceApplicationId}")
    public RaceApplication getRaceApplication(@PathVariable String raceApplicationId){ // todo implement me
        System.out.println(raceApplicationId);
        return null;
    }

    @PostMapping
    public void applyToRace(@RequestBody RaceDTO raceDTO) { // todo implement me
        System.out.println(raceDTO);
    }

    @DeleteMapping("/{raceApplicationId}")
    public List<Race> deleteRaceApplication(@PathVariable String raceApplicationId){ // todo implement me
        System.out.println(raceApplicationId);
        return List.of();
    }

}
