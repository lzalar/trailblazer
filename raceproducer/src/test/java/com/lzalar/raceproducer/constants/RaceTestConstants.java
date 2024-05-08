package com.lzalar.raceproducer.constants;

import com.lzalar.raceproducer.domain.race.Distance;
import com.lzalar.raceproducer.domain.race.Race;
import com.lzalar.raceproducer.web.dto.RaceDTO;

import java.util.UUID;

import static com.lzalar.raceproducer.domain.race.Distance.*;

public class RaceTestConstants {

    public static final UUID RACE_ID = UUID.fromString("217afbae-42b5-416d-b5e9-9ff92c28cb87");

    public static Race givenRace(){
        return givenRaceBuilder().build();
    }

    public static Race.RaceBuilder givenRaceBuilder(){
        return Race.builder()
                .id(RACE_ID)
                .name("testRace")
                .distance(MARATHON);
    }

    public static RaceDTO givenRaceDTO(){
        return givenRaceDTOBuilder().build();
    }

    public static RaceDTO.RaceDTOBuilder givenRaceDTOBuilder(){
        return RaceDTO.builder()
                .id(RACE_ID)
                .name("testRace")
                .distance(MARATHON);
    }
}
