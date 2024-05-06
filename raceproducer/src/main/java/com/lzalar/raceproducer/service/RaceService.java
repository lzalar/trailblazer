package com.lzalar.raceproducer.service;

import com.lzalar.raceproducer.domain.race.Race;
import com.lzalar.raceproducer.domain.race.RaceApplication;
import com.lzalar.raceproducer.repository.RaceApplicationRepository;
import com.lzalar.raceproducer.repository.RaceRepository;
import com.lzalar.raceproducer.web.dto.RaceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RaceService {

    private final RaceRepository raceRepository;
    private final RaceApplicationRepository raceApplicationRepository;
    private final RaceMapper raceMapper;


    public UUID createRace(RaceDTO raceDTO){
        Race race = raceMapper.mapFromDto(raceDTO);
        race.setId(null); // throw replace with validation

        return raceRepository.save(race).getId();
    }
    public void updateRace(UUID raceId, RaceDTO raceDTO) {
        Optional<Race> raceOptional = raceRepository.findById(raceId);

        if(raceOptional.isEmpty()){
            throw new IllegalArgumentException();
        }
        Race race = raceMapper.mapFromDto(raceDTO);
        race.setId(raceOptional.get().getId());

        raceRepository.save(race);
    }

    public void deleteRace(UUID raceId){
        Optional<Race> raceOptional = raceRepository.findById(raceId);
        if(raceOptional.isEmpty()){
            throw new IllegalArgumentException();
        }

        raceRepository.deleteById(raceId);
    }

    public void applyToRace(UUID raceID, RaceApplication raceApplication){

    }

    public void deleteRaceApplication(UUID raceApplicationId){
        Optional<RaceApplication> raceApplication = raceApplicationRepository.findById(raceApplicationId);
        if(raceApplication.isEmpty()){
            throw new IllegalArgumentException();
        }

        raceApplicationRepository.deleteById(raceApplicationId);
    }
}
