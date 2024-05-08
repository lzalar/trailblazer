package com.lzalar.raceproducer.service.mappers;

import com.lzalar.raceproducer.domain.race.Race;
import com.lzalar.raceproducer.domain.race.RaceApplication;
import com.lzalar.raceproducer.repository.RaceRepository;
import com.lzalar.raceproducer.web.dto.RaceApplicationDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.lzalar.raceproducer.constants.RaceApplicationTestConstants.givenRaceApplicationBuilder;
import static com.lzalar.raceproducer.constants.RaceApplicationTestConstants.givenRaceApplicationDTO;
import static com.lzalar.raceproducer.constants.RaceTestConstants.RACE_ID;
import static com.lzalar.raceproducer.constants.RaceTestConstants.givenRace;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RaceApplicationMapperTest {

    @InjectMocks
    RaceApplicationMapper raceApplicationMapper;
    @Mock
    RaceRepository raceRepository;

    @Test
    public void givenRaceDTO_mapFromDto_success() {
        Race race = givenRace();
        RaceApplication expected = givenRaceApplicationBuilder()
                .id(null)
                .race(race)
                .build();
        RaceApplicationDTO raceApplicationDTO = givenRaceApplicationDTO();

        when(raceRepository.getReferenceById(RACE_ID)).thenReturn(race);

        RaceApplication raceApplication = raceApplicationMapper.map(raceApplicationDTO);

        assertThat(raceApplication).isEqualTo(expected);
    }


}
