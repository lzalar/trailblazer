package com.lzalar.raceconsumer.constants;

import com.lzalar.raceconsumer.domain.RaceApplicationView;

import java.util.UUID;

import static com.lzalar.raceconsumer.constants.RaceViewTestContants.RACE_VIEW_ID;

public class RaceApplicationViewTestContants {

    public static final UUID RACE_APPLICATION_VIEW_ID = UUID.fromString("217afffe-42b5-400d-b5e9-9ff92c28cb87");
    public static final UUID USER_ID = UUID.fromString("000afffe-42b5-400d-b5e9-9ff92c28cb87");

    public static RaceApplicationView givenRaceApplicationView(){
        return givenRaceViewBuilder().build();
    }

    public static RaceApplicationView.RaceApplicationViewBuilder givenRaceViewBuilder(){
        return RaceApplicationView.builder()
                .raceApplicationId(RACE_APPLICATION_VIEW_ID)
                .firstName("jon")
                .lastName("doe")
                .club("prvaci")
                .raceId(RACE_VIEW_ID)
                .raceName("firstRace")
                .distance("MARATHON")
                .userId(USER_ID);
    }
}
