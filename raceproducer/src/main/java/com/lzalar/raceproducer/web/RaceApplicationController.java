package com.lzalar.raceproducer.web;

import com.lzalar.raceproducer.service.mappers.RaceApplicationMapper;
import com.lzalar.raceproducer.service.race.RaceService;
import com.lzalar.raceproducer.web.dto.RaceApplicationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/race-application")
@RequiredArgsConstructor
public class RaceApplicationController {


    private final RaceService raceService;
    private final RaceApplicationMapper raceApplicationMapper;

    @PostMapping("/{raceId}")
    public void applyToRace(@PathVariable UUID raceId, @RequestBody RaceApplicationDTO raceApplicationDTO){
        raceService.applyToRace(raceId,raceApplicationMapper.mapFromDto(raceApplicationDTO));
    }

    @DeleteMapping("/{raceApplicationId}")
    public void deleteRaceApplication(@PathVariable UUID raceApplicationId){
        raceService.deleteRaceApplication(raceApplicationId);
    }
}
