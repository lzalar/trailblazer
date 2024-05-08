package com.lzalar.raceproducer.constants;

import com.lzalar.raceproducer.domain.race.Race;
import com.lzalar.raceproducer.domain.race.RaceApplication;
import com.lzalar.raceproducer.domain.user.User;
import com.lzalar.raceproducer.web.dto.RaceApplicationDTO;

import java.util.UUID;

public class RaceApplicationTestConstants {

    public static final UUID RACE_APPLICATION_ID = UUID.fromString("550afbae-42b5-416d-b5e9-9ff92c28cb87");


    public static RaceApplication givenRaceApplication(){
        return givenRaceApplicationBuilder().build();
    }

    public static RaceApplication givenRaceApplication(User user, Race race){
        return givenRaceApplicationBuilder()
                .user(user)
                .race(race)
                .build();
    }

    public static RaceApplication.RaceApplicationBuilder givenRaceApplicationBuilder(){
        return RaceApplication.builder()
                .id(RACE_APPLICATION_ID)
                .firstName("jon")
                .lastName("doe")
                .club("club");
    }

    public static RaceApplicationDTO givenRaceApplicationDTO(){
        return givenRaceApplicationDTOBuilder().build();
    }

    public static RaceApplicationDTO.RaceApplicationDTOBuilder givenRaceApplicationDTOBuilder(){
        return RaceApplicationDTO.builder()
                .id(RACE_APPLICATION_ID)
                .firstName("jon")
                .lastName("doe")
                .club("club")
                .raceId(RaceTestConstants.RACE_ID);
    }
}
