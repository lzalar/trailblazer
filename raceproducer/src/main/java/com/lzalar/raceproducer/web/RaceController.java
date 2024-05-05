package com.lzalar.raceproducer.web;

import com.lzalar.clients.race.CreateRace;
import com.lzalar.raceproducer.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("/api/v1"))
@RequiredArgsConstructor
public class RaceController {

    private final MessageProducer messageProducer;


    @GetMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestParam("message") String message){
        messageProducer.sendMessage(new CreateRace("bla1","bla2", "bla3"));
        return ResponseEntity.ok("Message sent to RabbitMQ");
    }
}
