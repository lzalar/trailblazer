package com.lzalar.raceconsumer.constants;

import com.lzalar.raceconsumer.domain.RaceView;

import java.util.UUID;

public class RaceViewTestContants {

    public static final UUID RACE_VIEW_ID = UUID.fromString("217afbae-42b5-400d-b5e9-9ff92c28cb87");

    public static RaceView givenRaceView(){
        return givenRaceViewBuilder().build();
    }

    public static RaceView.RaceViewBuilder givenRaceViewBuilder(){
        return RaceView.builder()
                .id(RACE_VIEW_ID)
                .name("firstRace")
                .distance("MARATHON");
    }
}
