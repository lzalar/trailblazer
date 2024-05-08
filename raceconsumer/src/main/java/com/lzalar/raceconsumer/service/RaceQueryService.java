package com.lzalar.raceconsumer.service;

import com.lzalar.raceconsumer.domain.RaceApplicationView;
import com.lzalar.raceconsumer.domain.RaceView;
import com.lzalar.raceconsumer.domain.exception.TrailblazerException;
import com.lzalar.raceconsumer.repository.RaceApplicationViewRepository;
import com.lzalar.raceconsumer.repository.RaceViewRepository;
import com.lzalar.raceconsumer.web.dto.RaceApplicationPerUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.lzalar.raceconsumer.domain.exception.ErrorCode.RACE_APPLICATION_NOT_FOUND;
import static com.lzalar.raceconsumer.domain.exception.ErrorCode.RACE_NOT_FOUND;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RaceQueryService {

    private final RaceApplicationViewRepository raceApplicationViewRepository;
    private final RaceViewRepository raceViewRepository;

    public RaceApplicationView getRaceApplication(UUID raceApplicationId) {
        Optional<RaceApplicationView> raceApplicationView = raceApplicationViewRepository.findById(raceApplicationId);
        if(raceApplicationView.isEmpty()){
            throw new TrailblazerException(RACE_APPLICATION_NOT_FOUND);
        }
        return raceApplicationView.get();
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
        Optional<RaceView> raceView = raceViewRepository.findById(raceId);
        if(raceView.isEmpty()){
            throw new TrailblazerException(RACE_NOT_FOUND);
        }
        return raceView.get();
    }
}
