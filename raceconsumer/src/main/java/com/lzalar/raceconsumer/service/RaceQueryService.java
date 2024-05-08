package com.lzalar.raceconsumer.service;

import com.lzalar.raceconsumer.web.dto.RaceApplicationPerUserDTO;
import com.lzalar.raceconsumer.domain.RaceApplicationViewProjection;
import com.lzalar.raceconsumer.domain.RaceViewProjection;
import com.lzalar.raceconsumer.repository.RaceApplicationViewRepository;
import com.lzalar.raceconsumer.repository.RaceViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RaceQueryService {

    private final RaceApplicationViewRepository raceApplicationViewRepository;
    private final RaceViewRepository raceViewRepository;


    public RaceApplicationViewProjection getRaceApplication(UUID raceApplicationId) {
        return raceApplicationViewRepository.findById(raceApplicationId).get();
    }

    public RaceApplicationPerUserDTO getAllRaceApplicationsForUser(UUID userId) {
        return new RaceApplicationPerUserDTO(
                userId,
                raceApplicationViewRepository.findAllByUserId(userId)
        );
    }

    public List<RaceViewProjection> getAllRaces() {
        return raceViewRepository.findAll();
    }

    public RaceViewProjection getRace(UUID raceId) {
        return raceViewRepository.findById(raceId).get();
    }
}
