package com.lzalar.raceconsumer.service.projector;

import com.lzalar.clients.events.race.ApplicationEvent;
import com.lzalar.clients.events.race.CreateRaceEvent;
import com.lzalar.clients.events.race.DeleteRaceEvent;
import com.lzalar.clients.events.race.EditRaceEvent;
import com.lzalar.clients.events.race.application.CreateRaceApplicationEvent;
import com.lzalar.clients.events.race.application.DeleteRaceApplicationEvent;
import com.lzalar.raceconsumer.domain.RaceApplicationProjection;
import com.lzalar.raceconsumer.domain.RaceViewProjection;
import com.lzalar.raceconsumer.repository.RaceApplicationViewRepository;
import com.lzalar.raceconsumer.repository.RaceViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RaceApplicationViewProjector implements Projector {

    private final RaceApplicationViewRepository raceApplicationViewRepository;

    @Override
    public void process(ApplicationEvent applicationEvent) {
        switch (applicationEvent) {
            case CreateRaceApplicationEvent createRaceApplicationEvent -> handleCreateRaceApplicationEvent(createRaceApplicationEvent);
            case DeleteRaceApplicationEvent deleteRaceApplicationEvent -> handleDeleteRaceApplicationEvent(deleteRaceApplicationEvent);
            case EditRaceEvent editRaceEvent -> handleEditRaceEvent(editRaceEvent);
            case DeleteRaceEvent deleteRaceEvent -> handleDeleteRaceEvent(deleteRaceEvent);
            default -> {}
        }
    }

    private void handleCreateRaceApplicationEvent(CreateRaceApplicationEvent createRaceApplicationEvent) {
        raceApplicationViewRepository.save(new RaceApplicationProjection(createRaceApplicationEvent.getId(),
                createRaceApplicationEvent.getFirstName(),
                createRaceApplicationEvent.getLastName(),
                createRaceApplicationEvent.getClub(),
                createRaceApplicationEvent.getRaceId(),
                createRaceApplicationEvent.getRaceName(),
                createRaceApplicationEvent.getDistance()
                ));
    }

    private void handleDeleteRaceApplicationEvent(DeleteRaceApplicationEvent deleteRaceApplicationEvent) {
        raceApplicationViewRepository.deleteById(deleteRaceApplicationEvent.getRaceApplicationId());
    }


    private void handleDeleteRaceEvent(DeleteRaceEvent deleteRaceEvent) {
        raceApplicationViewRepository.deleteByRace(deleteRaceEvent.getRaceId());
    }

    private void handleEditRaceEvent(EditRaceEvent editRaceEvent) {
        raceApplicationViewRepository.updateRaceInformation(editRaceEvent.getName(),editRaceEvent.getDistance(),editRaceEvent.getRaceId());
    }
}
