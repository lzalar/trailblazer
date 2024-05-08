package com.lzalar.raceproducer.service.race;

import com.lzalar.clients.events.race.CreateRaceEvent;
import com.lzalar.clients.events.race.DeleteRaceEvent;
import com.lzalar.clients.events.race.EditRaceEvent;
import com.lzalar.clients.events.raceapplication.CreateRaceApplicationEvent;
import com.lzalar.clients.events.raceapplication.DeleteRaceApplicationEvent;
import com.lzalar.raceproducer.domain.exception.TrailblazerException;
import com.lzalar.raceproducer.domain.race.Race;
import com.lzalar.raceproducer.domain.race.RaceApplication;
import com.lzalar.raceproducer.service.ampq.RabbitMessageService;
import com.lzalar.raceproducer.repository.RaceApplicationRepository;
import com.lzalar.raceproducer.repository.RaceRepository;
import com.lzalar.raceproducer.service.auth.AuthenticationService;
import com.lzalar.raceproducer.service.mappers.RaceApplicationMapper;
import com.lzalar.raceproducer.service.mappers.RaceMapper;
import com.lzalar.raceproducer.web.dto.RaceApplicationDTO;
import com.lzalar.raceproducer.web.dto.RaceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.lzalar.raceproducer.domain.exception.ErrorCode.*;

@Component
@RequiredArgsConstructor
@Transactional
public class RaceService {

    private final RaceRepository raceRepository;
    private final RaceApplicationRepository raceApplicationRepository;
    private final RaceMapper raceMapper;
    private final RaceApplicationMapper raceApplicationMapper;
    private final RabbitMessageService rabbitMessageService;
    private final AuthenticationService authenticationService;
    private final UuidGeneratorService uuidGeneratorService;



    public UUID createRace(RaceDTO raceDTO) {
        Race race = raceMapper.mapFromDto(raceDTO);
        if (raceDTO.id() != null) {
            throw new TrailblazerException(RACE_ID_SHOULD_NOT_BE_PROVIDED);
        }

        race = raceRepository.save(race);
        rabbitMessageService.sendMessage(new CreateRaceEvent(uuidGeneratorService.generateRandomUUID(), race.getId(), race.getName(), race.getDistance().name()));
        return race.getId();
    }

    public void updateRace(UUID raceId, RaceDTO raceDTO) {
        if (!raceId.equals(raceDTO.id())) {
            throw new TrailblazerException(RACE_IDS_DO_NOT_MATCH);
        }

        Optional<Race> raceOptional = raceRepository.findById(raceId);

        validateRaceExists(raceOptional);

        Race race = raceMapper.mapFromDto(raceDTO);

        raceRepository.save(race);

        rabbitMessageService.sendMessage(new EditRaceEvent(uuidGeneratorService.generateRandomUUID(), race.getId(), race.getName(), race.getDistance().name()));
    }

    public void deleteRace(UUID raceId) {
        Optional<Race> raceOptional = raceRepository.findById(raceId);

        validateRaceExists(raceOptional);

        raceApplicationRepository.deleteRaceApplicationByRace(raceOptional.get());
        raceRepository.deleteById(raceId);

        rabbitMessageService.sendMessage(new DeleteRaceEvent(uuidGeneratorService.generateRandomUUID(),raceId));
    }

    private static void validateRaceExists(Optional<Race> raceOptional) {
        if (raceOptional.isEmpty()) {
            throw new TrailblazerException(RACE_NOT_FOUND);
        }
    }

    public void applyToRace(UUID raceId, RaceApplicationDTO raceApplicationDTO) {
        Optional<Race> raceOptional = raceRepository.findById(raceId);

        validateRaceExists(raceOptional);

        Race race = raceOptional.get();

        RaceApplication raceApplication = raceApplicationMapper.map(raceApplicationDTO);
        raceApplication.setUser(authenticationService.getCurrentUser());

        raceApplication = raceApplicationRepository.save(raceApplication);

        rabbitMessageService.sendMessage(new CreateRaceApplicationEvent(uuidGeneratorService.generateRandomUUID(), raceApplication.getId(), raceApplication.getFirstName(), raceApplication.getLastName(), raceApplication.getClub(), raceId, race.getName(), race.getDistance().name(), raceApplication.getUser().getId()));
    }

    public void deleteRaceApplication(UUID raceApplicationId) {
        Optional<RaceApplication> raceApplication = raceApplicationRepository.findById(raceApplicationId);
        if (raceApplication.isEmpty()) {
            throw new TrailblazerException(RACE_APPLICATION_NOT_FOUND);
        }

        raceApplicationRepository.deleteById(raceApplicationId);
        rabbitMessageService.sendMessage(new DeleteRaceApplicationEvent(uuidGeneratorService.generateRandomUUID(), raceApplicationId));
    }
}
