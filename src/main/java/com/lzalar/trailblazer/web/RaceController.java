package com.lzalar.trailblazer.web;

import com.lzalar.trailblazer.domain.race.Race;
import com.lzalar.trailblazer.service.RaceService;
import com.lzalar.trailblazer.web.dto.RaceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/race")
@RequiredArgsConstructor
public class RaceController {

    private final RaceService raceService;

    @GetMapping
    public List<Race> getAllRaces(){ // todo implement me
        return List.of();
    }

    @GetMapping("/{raceId}")
    public List<Race> getSingleRace(@PathVariable String raceId){ // todo implement me
        System.out.println(raceId);
        return List.of();
    }

    @PostMapping
    public List<Race> createRace(@RequestBody RaceDTO raceDTO){ // todo implement me
        System.out.println(raceDTO);
        return List.of();
    }
    @PutMapping("/{raceId}")
    public List<Race> editRace(@PathVariable String raceId){ // todo implement me
        System.out.println(raceId);
        return List.of();
    }

    @DeleteMapping("/{raceId}")
    public List<Race> deleteRace(@PathVariable String raceId){ // todo implement me
        System.out.println(raceId);
        return List.of();
    }

}
