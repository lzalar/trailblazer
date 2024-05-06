package com.lzalar.raceproducer.web;

import com.lzalar.clients.race.CreateRace;
import com.lzalar.raceproducer.domain.race.Race;
import com.lzalar.raceproducer.producer.MessageProducer;
import com.lzalar.raceproducer.service.RaceService;
import com.lzalar.raceproducer.web.dto.RaceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/api/v1"))
@RequiredArgsConstructor
public class RaceController {

    private final RaceService raceService;
    private final MessageProducer messageProducer;


    @GetMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestParam("message") String message){
        messageProducer.sendMessage(new CreateRace("bla1","bla2", "bla3"));
        return ResponseEntity.ok("Message sent to RabbitMQ");
    }



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
