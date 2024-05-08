package com.lzalar.raceproducer.service.race;

import com.lzalar.clients.events.race.CreateRaceEvent;
import com.lzalar.clients.events.race.DeleteRaceEvent;
import com.lzalar.clients.events.race.EditRaceEvent;
import com.lzalar.clients.events.raceapplication.CreateRaceApplicationEvent;
import com.lzalar.clients.events.raceapplication.DeleteRaceApplicationEvent;
import com.lzalar.raceproducer.domain.exception.TrailblazerException;
import com.lzalar.raceproducer.domain.race.Race;
import com.lzalar.raceproducer.domain.race.RaceApplication;
import com.lzalar.raceproducer.repository.RaceApplicationRepository;
import com.lzalar.raceproducer.repository.RaceRepository;
import com.lzalar.raceproducer.service.ampq.RabbitMessageService;
import com.lzalar.raceproducer.service.auth.AuthenticationService;
import com.lzalar.raceproducer.service.mappers.RaceApplicationMapper;
import com.lzalar.raceproducer.service.mappers.RaceMapper;
import com.lzalar.raceproducer.web.dto.RaceApplicationDTO;
import com.lzalar.raceproducer.web.dto.RaceDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static com.lzalar.raceproducer.constants.RaceApplicationTestConstants.*;
import static com.lzalar.raceproducer.constants.RaceTestConstants.*;
import static com.lzalar.raceproducer.constants.UserTestConstants.USER_ID;
import static com.lzalar.raceproducer.constants.UserTestConstants.givenUser;
import static com.lzalar.raceproducer.domain.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RaceServiceTest {

    @InjectMocks
    private RaceService raceService;
    @Mock
    private RaceRepository raceRepository;
    @Mock
    private RaceApplicationRepository raceApplicationRepository;
    @Mock
    private RaceMapper raceMapper;
    @Mock
    private RaceApplicationMapper raceApplicationMapper;
    @Mock
    private RabbitMessageService rabbitMessageService;
    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private UuidGeneratorService uuidGeneratorService;

    private static final UUID EVENT_UUID = UUID.randomUUID();

    @Test
    void givenRaceIdProvidedInDto_createRace_throws() {
        assertThatCode(() -> raceService.createRace(givenRaceDTO()))
                .isInstanceOf(TrailblazerException.class)
                .hasMessage(RACE_ID_SHOULD_NOT_BE_PROVIDED.getMessage());
    }

    @Test
    void givenRaceIdProvidedInDto_createRace_createRace() {
        RaceDTO raceDto = givenRaceDTOBuilder().id(null).build();
        Race race = givenRace();
        CreateRaceEvent createRaceEvent = new CreateRaceEvent(EVENT_UUID,
                RACE_ID,
                race.getName(),
                race.getDistance().name());

        when(raceMapper.mapFromDto(raceDto)).thenReturn(race);
        when(raceRepository.save(race)).thenReturn(race);
        when(uuidGeneratorService.generateRandomUUID()).thenReturn(EVENT_UUID);

        UUID createdRaceId = raceService.createRace(raceDto);

        assertThat(createdRaceId).isEqualByComparingTo(race.getId());
        verify(rabbitMessageService).sendMessage(createRaceEvent);
    }

    @Test
    void givenValidRaceDTO_updateRace_success() {
        RaceDTO raceDto = givenRaceDTO();
        Race race = givenRace();
        EditRaceEvent editRaceEvent = new EditRaceEvent(EVENT_UUID,
                RACE_ID,
                race.getName(),
                race.getDistance().name());

        when(raceMapper.mapFromDto(raceDto)).thenReturn(race);
        when(raceRepository.findById(RACE_ID)).thenReturn(Optional.of(race));
        when(raceRepository.save(race)).thenReturn(race);
        when(uuidGeneratorService.generateRandomUUID()).thenReturn(EVENT_UUID);

        raceService.updateRace(RACE_ID,raceDto);

        verify(raceRepository).save(race);
        verify(rabbitMessageService).sendMessage(editRaceEvent);
    }

    @Test
    void givenRaceIdAndDtoRaceIdDoNotMatch_createRace_throws() {
        assertThatCode(() -> raceService.updateRace(UUID.randomUUID(), givenRaceDTO()))
                .isInstanceOf(TrailblazerException.class)
                .hasMessage(RACE_IDS_DO_NOT_MATCH.getMessage());
    }

    @Test
    void givenRaceDoesNotExist_editRace_throws() {
        when(raceRepository.findById(RACE_ID)).thenReturn(Optional.empty());

        assertThatCode(() -> raceService.updateRace(RACE_ID, givenRaceDTO()))
                .isInstanceOf(TrailblazerException.class)
                .hasMessage(RACE_NOT_FOUND.getMessage());
    }

    @Test
    void givenValidId_deleteRace_success() {
        Race race = givenRace();
        DeleteRaceEvent deleteRaceEvent =  new DeleteRaceEvent(EVENT_UUID, RACE_ID);
        when(raceRepository.findById(RACE_ID)).thenReturn(Optional.of(race));
        when(uuidGeneratorService.generateRandomUUID()).thenReturn(EVENT_UUID);

        raceService.deleteRace(RACE_ID);

        verify(raceApplicationRepository).deleteRaceApplicationByRace(race);
        verify(raceRepository).deleteById(RACE_ID);
        verify(rabbitMessageService).sendMessage(deleteRaceEvent);
    }

    @Test
    void givenRaceDoesNotExist_deleteRace_throws() {
        when(raceRepository.findById(RACE_ID)).thenReturn(Optional.empty());

        assertThatCode(() -> raceService.deleteRace(RACE_ID))
                .isInstanceOf(TrailblazerException.class)
                .hasMessage(RACE_NOT_FOUND.getMessage());
    }

    @Test
    void givenValidRaceApplication_applyToRace_success() {
        RaceApplicationDTO raceApplicationDTO = givenRaceApplicationDTO();
        RaceApplication raceApplication = givenRaceApplication();
        Race race = givenRace();
        CreateRaceApplicationEvent createRaceApplicationEvent = new CreateRaceApplicationEvent(EVENT_UUID,
                RACE_APPLICATION_ID,
                raceApplicationDTO.firstName(),
                raceApplicationDTO.lastName(),
                raceApplicationDTO.club(),
                race.getId(),
                race.getName(),
                race.getDistance().name(),
                USER_ID);

        when(raceRepository.findById(RACE_ID)).thenReturn(Optional.of(race));
        when(raceApplicationMapper.map(raceApplicationDTO)).thenReturn(raceApplication);
        when(authenticationService.getCurrentUser()).thenReturn(givenUser());
        when(raceApplicationRepository.save(raceApplication)).thenReturn(raceApplication);
        when(uuidGeneratorService.generateRandomUUID()).thenReturn(EVENT_UUID);

        raceService.applyToRace(RACE_ID, raceApplicationDTO);

        verify(rabbitMessageService).sendMessage(createRaceApplicationEvent);
    }

    @Test
    void givenRaceDoesNotExist_applyToRace_throws() {
        when(raceRepository.findById(RACE_ID)).thenReturn(Optional.empty());

        assertThatCode(() -> raceService.applyToRace(RACE_ID, null))
                .isInstanceOf(TrailblazerException.class)
                .hasMessage(RACE_NOT_FOUND.getMessage());
    }

    @Test
    void givenRaceApplicationExists_deleteRaceApplication_success() {
        RaceApplication raceApplication = givenRaceApplication();
        DeleteRaceApplicationEvent deleteRaceApplicationEvent =  new DeleteRaceApplicationEvent(EVENT_UUID, RACE_APPLICATION_ID);
        when(raceApplicationRepository.findById(RACE_APPLICATION_ID)).thenReturn(Optional.of(raceApplication));
        when(uuidGeneratorService.generateRandomUUID()).thenReturn(EVENT_UUID);

        raceService.deleteRaceApplication(RACE_APPLICATION_ID);

        verify(raceApplicationRepository).deleteById(RACE_APPLICATION_ID);
        verify(rabbitMessageService).sendMessage(deleteRaceApplicationEvent);
    }

    @Test
    void givenRaceApplicationDoesNotExist_deleteRaceApplication_throws() {
        when(raceApplicationRepository.findById(RACE_APPLICATION_ID)).thenReturn(Optional.empty());

        assertThatCode(() -> raceService.deleteRaceApplication(RACE_APPLICATION_ID))
                .isInstanceOf(TrailblazerException.class)
                .hasMessage(RACE_APPLICATION_NOT_FOUND.getMessage());
    }
}
