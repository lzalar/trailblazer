package com.lzalar.raceproducer.service.race;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidGeneratorService {

    public UUID generateRandomUUID(){
        return UUID.randomUUID();
    }
}
