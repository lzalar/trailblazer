package com.lzalar.raceconsumer.service;

import com.lzalar.raceconsumer.web.dto.RaceApplicationPerUserDTO;
import com.lzalar.raceconsumer.domain.RaceApplicationView;
import com.lzalar.raceconsumer.domain.RaceView;
import com.lzalar.raceconsumer.repository.RaceApplicationViewRepository;
import com.lzalar.raceconsumer.repository.RaceViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    public RaceApplicationView getRaceApplication(UUID raceApplicationId) {
        return raceApplicationViewRepository.findById(raceApplicationId).get();
    }

    public RaceApplicationPerUserDTO getAllRaceApplicationsForUser(UUID userId) {
        return new RaceApplicationPerUserDTO(
                userId,
                raceApplicationViewRepository.findAllByUserId(userId)
        );
    }

    public Page<RaceView> getAllRaces(Pageable pageable) {
        return raceViewRepository.findAll(pageable);
    }

    public RaceView getRace(UUID raceId) {
        return raceViewRepository.findById(raceId).get();
    }
}
