package com.lzalar.raceconsumer.service.projector;

import com.lzalar.clients.events.ApplicationEvent;
import com.lzalar.clients.events.race.DeleteRaceEvent;
import com.lzalar.clients.events.race.EditRaceEvent;
import com.lzalar.clients.events.raceapplication.CreateRaceApplicationEvent;
import com.lzalar.clients.events.raceapplication.DeleteRaceApplicationEvent;
import com.lzalar.raceconsumer.domain.RaceApplicationView;
import com.lzalar.raceconsumer.repository.RaceApplicationViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
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
        raceApplicationViewRepository.save(new RaceApplicationView(createRaceApplicationEvent.getRaceApplicationId(),
                createRaceApplicationEvent.getFirstName(),
                createRaceApplicationEvent.getLastName(),
                createRaceApplicationEvent.getClub(),
                createRaceApplicationEvent.getRaceId(),
                createRaceApplicationEvent.getRaceName(),
                createRaceApplicationEvent.getDistance(),
                createRaceApplicationEvent.getUserId()
                ));
    }

    private void handleDeleteRaceApplicationEvent(DeleteRaceApplicationEvent deleteRaceApplicationEvent) {
        raceApplicationViewRepository.deleteById(deleteRaceApplicationEvent.getRaceApplicationId());
    }


    private void handleDeleteRaceEvent(DeleteRaceEvent deleteRaceEvent) {
        raceApplicationViewRepository.deleteByRaceId(deleteRaceEvent.getRaceId());
    }

    private void handleEditRaceEvent(EditRaceEvent editRaceEvent) {
        raceApplicationViewRepository.updateRaceInformation(editRaceEvent.getName(),editRaceEvent.getDistance(),editRaceEvent.getRaceId());
    }
}
