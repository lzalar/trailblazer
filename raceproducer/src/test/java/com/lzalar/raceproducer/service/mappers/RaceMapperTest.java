package com.lzalar.raceproducer.service.mappers;

import com.lzalar.raceproducer.domain.race.Race;
import com.lzalar.raceproducer.web.dto.RaceDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.lzalar.raceproducer.constants.RaceTestConstants.givenRace;
import static com.lzalar.raceproducer.constants.RaceTestConstants.givenRaceDTO;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RaceMapperTest {

    @InjectMocks
    RaceMapper raceMapper;

    @Test
    public void givenRaceDTO_mapFromDto_success() {

        Race expected = givenRace();
        RaceDTO raceDTO = givenRaceDTO();

        Race race = raceMapper.mapFromDto(raceDTO);

        assertThat(race).isEqualTo(expected);
    }


}
