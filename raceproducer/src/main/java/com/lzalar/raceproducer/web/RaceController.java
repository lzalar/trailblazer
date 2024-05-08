package com.lzalar.raceproducer.web;

import com.lzalar.raceproducer.service.race.RaceService;
import com.lzalar.raceproducer.web.dto.RaceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(("/api/v1/race"))
@RequiredArgsConstructor
public class RaceController {

    private final RaceService raceService;

    @PostMapping
    @PreAuthorize("hasRole('administrator')")
    public UUID createRace(@RequestBody RaceDTO raceDTO) {
        return raceService.createRace(raceDTO);
    }

    @PutMapping("/{raceId}")
    @PreAuthorize("hasRole('administrator')")
    public void editRace(@PathVariable UUID raceId, @RequestBody RaceDTO raceDTO) {
        raceService.updateRace(raceId, raceDTO);
    }

    @DeleteMapping("/{raceId}")
    @PreAuthorize("hasRole('administrator')")
    public void deleteRace(@PathVariable UUID raceId) {
        raceService.deleteRace(raceId);
    }
}
