package com.lzalar.raceproducer.service;

import com.lzalar.raceproducer.domain.race.RaceApplication;
import com.lzalar.raceproducer.repository.RaceRepository;
import com.lzalar.raceproducer.web.dto.RaceApplicationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RaceApplicationMapper {

    private final RaceRepository raceRepository;


    public RaceApplication mapFromDto(RaceApplicationDTO raceApplicationDTO){
        return new RaceApplication(
                raceApplicationDTO.id(),
                raceApplicationDTO.firstName(),
                raceApplicationDTO.lastName(),
                raceApplicationDTO.club(),
                raceRepository.getReferenceById(raceApplicationDTO.raceId())
        );
    }

    public RaceApplicationDTO mapToDto(RaceApplication raceApplication){
        return new RaceApplicationDTO(
                raceApplication.getId(),
                raceApplication.getFirstName(),
                raceApplication.getLastName(),
                raceApplication.getClub(),
                raceApplication.getRace().getId()
        );
    }
}
