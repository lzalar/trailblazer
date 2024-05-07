package com.lzalar.raceproducer.web;

import com.lzalar.raceproducer.service.RaceService;
import com.lzalar.raceproducer.web.dto.RaceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(("/api/v1/race"))
@RequiredArgsConstructor
public class RaceController {

    private final RaceService raceService;

    @PostMapping
    public UUID createRace(@RequestBody RaceDTO raceDTO) {
        return raceService.createRace(raceDTO);
    }

    @PutMapping("/{raceId}")
    public void editRace(@PathVariable UUID raceId, @RequestBody RaceDTO raceDTO) {
        raceService.updateRace(raceId, raceDTO);
    }

    @DeleteMapping("/{raceId}")
    public ResponseEntity<Void> deleteRace(@PathVariable UUID raceId) {
        raceService.deleteRace(raceId);
        return ResponseEntity.noContent().build();
    }
}
