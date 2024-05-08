package com.lzalar.raceconsumer.service;

import com.lzalar.raceconsumer.domain.RaceApplicationView;
import com.lzalar.raceconsumer.domain.RaceView;
import com.lzalar.raceconsumer.domain.exception.TrailblazerException;
import com.lzalar.raceconsumer.repository.RaceApplicationViewRepository;
import com.lzalar.raceconsumer.repository.RaceViewRepository;
import com.lzalar.raceconsumer.web.dto.RaceApplicationPerUserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.lzalar.raceconsumer.constants.RaceApplicationViewTestContants.givenRaceApplicationView;
import static com.lzalar.raceconsumer.constants.RaceViewTestContants.givenRaceView;
import static com.lzalar.raceconsumer.domain.exception.ErrorCode.RACE_APPLICATION_NOT_FOUND;
import static com.lzalar.raceconsumer.domain.exception.ErrorCode.RACE_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RaceQueryServiceTest {

    @Mock
    private RaceApplicationViewRepository raceApplicationViewRepository;

    @Mock
    private RaceViewRepository raceViewRepository;

    @InjectMocks
    private RaceQueryService raceQueryService;
    @Mock
    private Pageable pageable;

    @Test
    void raceApplicationExists_getRaceApplication_success() {
        UUID raceApplicationId = UUID.randomUUID();
        RaceApplicationView raceApplicationView = givenRaceApplicationView();
        when(raceApplicationViewRepository.findById(raceApplicationId)).thenReturn(Optional.of(raceApplicationView));

        RaceApplicationView result = raceQueryService.getRaceApplication(raceApplicationId);

        assertThat(result).isEqualTo(raceApplicationView);
    }

    @Test
    void raceApplicationDoesNotExists_getRaceApplication_throws() {
        UUID raceApplicationId = UUID.randomUUID();
        when(raceApplicationViewRepository.findById(raceApplicationId)).thenReturn(Optional.empty());

        assertThatCode(()->raceQueryService.getRaceApplication(raceApplicationId))
                .isInstanceOf(TrailblazerException.class)
                .hasMessage(RACE_APPLICATION_NOT_FOUND.getMessage());
    }

    @Test
    void getAllRaceApplicationsForUser_success() {
        UUID userId = UUID.randomUUID();
        RaceApplicationView raceApplicationView = givenRaceApplicationView();

        when(raceApplicationViewRepository.findAllByUserId(userId)).thenReturn(List.of(raceApplicationView));

        RaceApplicationPerUserDTO result = raceQueryService.getAllRaceApplicationsForUser(userId);

        assertThat(result.getRaceApplications()).hasSize(1);
        assertThat(result.getRaceApplications().get(0)).isEqualTo(raceApplicationView);
    }

    @Test
    void getAllRaces_success() {
        List<RaceView> raceViews = List.of(givenRaceView());
        when(raceViewRepository.findAll(pageable)).thenReturn(new PageImpl<>(raceViews));

        Page<RaceView> result = raceQueryService.getAllRaces(pageable);

        assertThat(result.getContent()).isEqualTo(raceViews);
    }

    @Test
    void raceExists_getRace_success() {
        UUID raceId = UUID.randomUUID();
        RaceView raceView = givenRaceView();
        when(raceViewRepository.findById(raceId)).thenReturn(Optional.of(raceView));

        RaceView result = raceQueryService.getRace(raceId);

        assertThat(result).isEqualTo(raceView);
    }

    @Test
    void raceDoesNotExists_getRace_throws() {
        UUID raceId = UUID.randomUUID();
        when(raceViewRepository.findById(raceId)).thenReturn(Optional.empty());

        assertThatCode(()->raceQueryService.getRace(raceId))
                .isInstanceOf(TrailblazerException.class)
                .hasMessage(RACE_NOT_FOUND.getMessage());
    }
}


