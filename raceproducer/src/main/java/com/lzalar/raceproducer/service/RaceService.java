package com.lzalar.raceproducer.service;

import com.lzalar.clients.events.race.CreateRaceEvent;
import com.lzalar.clients.events.race.DeleteRaceEvent;
import com.lzalar.clients.events.race.EditRaceEvent;
import com.lzalar.clients.events.race.application.CreateRaceApplicationEvent;
import com.lzalar.clients.events.race.application.DeleteRaceApplicationEvent;
import com.lzalar.raceproducer.domain.race.Race;
import com.lzalar.raceproducer.domain.race.RaceApplication;
import com.lzalar.raceproducer.producer.MessageProducer;
import com.lzalar.raceproducer.repository.RaceApplicationRepository;
import com.lzalar.raceproducer.repository.RaceRepository;
import com.lzalar.raceproducer.repository.UserRepository;
import com.lzalar.raceproducer.web.dto.RaceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional
public class RaceService {

    private final RaceRepository raceRepository;
    private final UserRepository userRepository;
    private final RaceApplicationRepository raceApplicationRepository;
    private final RaceMapper raceMapper;
    private final MessageProducer messageProducer;


    public UUID createRace(RaceDTO raceDTO) {
        Race race = raceMapper.mapFromDto(raceDTO);
        race.setId(null); // throw replace with validation

        race = raceRepository.save(race);
        messageProducer.sendMessage(new CreateRaceEvent(UUID.randomUUID(), race.getId(), race.getName(), race.getDistance().name()));
        return race.getId();
    }

    public void updateRace(UUID raceId, RaceDTO raceDTO) {
        Optional<Race> raceOptional = raceRepository.findById(raceId);

        if (raceOptional.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Race race = raceMapper.mapFromDto(raceDTO);
        race.setId(raceOptional.get().getId());

        raceRepository.save(race);

        messageProducer.sendMessage(new EditRaceEvent(UUID.randomUUID(), race.getId(), race.getName(), race.getDistance().name()));
    }

    public void deleteRace(UUID raceId) {
        Optional<Race> raceOptional = raceRepository.findById(raceId);
        if (raceOptional.isEmpty()) {
            throw new IllegalArgumentException();
        }

        raceApplicationRepository.deleteRaceApplicationByRace(raceOptional.get());
        raceRepository.deleteById(raceId);

        messageProducer.sendMessage(new DeleteRaceEvent(UUID.randomUUID(),raceId));
    }

    public void applyToRace(UUID raceId, RaceApplication raceApplication) {
        Race race = raceRepository.findById(raceId).get();
        raceApplication.setUser(userRepository.findAll().get(0));
        raceApplication = raceApplicationRepository.save(raceApplication);

        messageProducer.sendMessage(new CreateRaceApplicationEvent(UUID.randomUUID(), raceApplication.getId(), raceApplication.getFirstName(), raceApplication.getLastName(), raceApplication.getClub(), raceId, race.getName(), race.getDistance().name(), raceApplication.getUser().getId()));
    }

    public void deleteRaceApplication(UUID raceApplicationId) {
        Optional<RaceApplication> raceApplication = raceApplicationRepository.findById(raceApplicationId);
        if (raceApplication.isEmpty()) {
            throw new IllegalArgumentException(); // todo error codes
        }

        raceApplicationRepository.deleteById(raceApplicationId);
        messageProducer.sendMessage(new DeleteRaceApplicationEvent(UUID.randomUUID(), raceApplicationId));
    }
}
