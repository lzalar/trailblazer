package com.lzalar.raceproducer.service.mappers;

import com.lzalar.raceproducer.domain.race.RaceApplication;
import com.lzalar.raceproducer.repository.RaceRepository;
import com.lzalar.raceproducer.repository.UserRepository;
import com.lzalar.raceproducer.web.dto.RaceApplicationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RaceApplicationMapper {

    private final RaceRepository raceRepository;


    public RaceApplication map(RaceApplicationDTO raceApplicationDTO) {

        return new RaceApplication(
                null,
                raceApplicationDTO.firstName(),
                raceApplicationDTO.lastName(),
                raceApplicationDTO.club(),
                raceRepository.getReferenceById(raceApplicationDTO.raceId()),
                null
        );
    }
}
