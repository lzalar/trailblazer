package com.lzalar.raceproducer.service.mappers;

import com.lzalar.raceproducer.domain.race.Race;
import com.lzalar.raceproducer.web.dto.RaceDTO;
import org.springframework.stereotype.Component;

@Component
public class RaceMapper {


    public Race mapFromDto(RaceDTO raceDTO){
        return Race.builder()
                .id(raceDTO.id())
                .name(raceDTO.name())
                .distance(raceDTO.distance())
                .build();
    }

    public RaceDTO mapToDto(Race race){
        return new RaceDTO(
                race.getId(),
                race.getName(),
                race.getDistance()
        );
    }
}
