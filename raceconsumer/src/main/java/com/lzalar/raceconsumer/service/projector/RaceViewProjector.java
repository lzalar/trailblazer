package com.lzalar.raceconsumer.service.projector;

import com.lzalar.clients.events.race.ApplicationEvent;
import com.lzalar.clients.events.race.CreateRaceEvent;
import com.lzalar.clients.events.race.EditRaceEvent;
import com.lzalar.raceconsumer.domain.RaceViewProjection;
import com.lzalar.raceconsumer.repository.RaceViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RaceViewProjector implements Projector {

    private final RaceViewRepository raceViewRepository;

    @Override
    public void process(ApplicationEvent applicationEvent) {
        switch (applicationEvent) {
            case CreateRaceEvent createRaceEvent -> handleCreateRaceEvent(createRaceEvent);
            case EditRaceEvent editRaceEvent -> handleEditRaceEvent(editRaceEvent);
            default -> {
            }
        }
    }


    private void handleCreateRaceEvent(CreateRaceEvent createRaceEvent) {
        raceViewRepository.save(new RaceViewProjection(createRaceEvent.getRaceId(), createRaceEvent.getName(), createRaceEvent.getDistance()));
    }

    private void handleEditRaceEvent(EditRaceEvent editRaceEvent) {
        Optional<RaceViewProjection> raceViewOptional = raceViewRepository.findById(editRaceEvent.getRaceId());
        if (raceViewOptional.isEmpty()) {
            log.error("race with id: {} does not exist", editRaceEvent.getRaceId());
            return;
        }

        RaceViewProjection raceViewProjection = raceViewOptional.get();

        raceViewProjection.setName(editRaceEvent.getName());
        raceViewProjection.setDistance(editRaceEvent.getDistance());
        raceViewRepository.save(raceViewProjection);
    }
}
