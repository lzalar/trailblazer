package com.lzalar.raceconsumer.service.projector;

import com.lzalar.clients.events.ApplicationEvent;
import com.lzalar.clients.events.race.CreateRaceEvent;
import com.lzalar.clients.events.race.DeleteRaceEvent;
import com.lzalar.clients.events.race.EditRaceEvent;
import com.lzalar.raceconsumer.domain.RaceView;
import com.lzalar.raceconsumer.repository.RaceViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class RaceViewProjector implements Projector {

    private final RaceViewRepository raceViewRepository;

    @Override
    public void process(ApplicationEvent applicationEvent) {
        switch (applicationEvent) {
            case CreateRaceEvent createRaceEvent -> handleCreateRaceEvent(createRaceEvent);
            case EditRaceEvent editRaceEvent -> handleEditRaceEvent(editRaceEvent);
            case DeleteRaceEvent deleteRaceEvent -> handleDeleteRaceEvent(deleteRaceEvent);
            default -> {
            }
        }
    }

    private void handleDeleteRaceEvent(DeleteRaceEvent deleteRaceEvent) {
        raceViewRepository.deleteById(deleteRaceEvent.getRaceId()); // todo delete hanging applications
    }


    private void handleCreateRaceEvent(CreateRaceEvent createRaceEvent) {
        raceViewRepository.save(new RaceView(createRaceEvent.getRaceId(), createRaceEvent.getName(), createRaceEvent.getDistance()));
    }

    private void handleEditRaceEvent(EditRaceEvent editRaceEvent) {
        Optional<RaceView> raceViewOptional = raceViewRepository.findById(editRaceEvent.getRaceId());
        if (raceViewOptional.isEmpty()) {
            log.error("race with id: {} does not exist", editRaceEvent.getRaceId());
            return;
        }

        RaceView raceView = raceViewOptional.get();

        raceView.setName(editRaceEvent.getName());
        raceView.setDistance(editRaceEvent.getDistance());
        raceViewRepository.save(raceView);
    }
}
