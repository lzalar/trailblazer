package com.lzalar.raceconsumer.service.projector;

import com.lzalar.clients.events.race.CreateRaceEvent;
import com.lzalar.clients.events.race.DeleteRaceEvent;
import com.lzalar.clients.events.race.EditRaceEvent;
import com.lzalar.clients.events.raceapplication.CreateRaceApplicationEvent;
import com.lzalar.raceconsumer.BaseIntegrationTest;
import com.lzalar.raceconsumer.constants.RaceViewTestContants;
import com.lzalar.raceconsumer.domain.RaceView;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@ExtendWith(MockitoExtension.class)
class RaceViewProjectorIntegrationTest extends BaseIntegrationTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    void deleteRaceEvent_deleteRaceViewWithRaceId() {
        RaceView raceView = RaceViewTestContants.givenRaceView();
        raceViewRepository.save(raceView);

        assertThat(raceViewRepository.findAll().isEmpty()).isFalse();

        rabbitTemplate.convertAndSend(exchange, routingKey, new DeleteRaceEvent(UUID.randomUUID(), raceView.getId()));

        await().atMost(5, TimeUnit.SECONDS).until(() -> raceViewRepository.findAll().isEmpty());
    }

    @Test
    void createRaceEvent_createRaceView() {
        RaceView expected = RaceViewTestContants.givenRaceView();

        assertThat(raceViewRepository.findAll().isEmpty()).isTrue();

        rabbitTemplate.convertAndSend(exchange, routingKey, new CreateRaceEvent(UUID.randomUUID(), expected.getId(), expected.getName(), expected.getDistance()));

        await().atMost(5, TimeUnit.SECONDS).until(
                () -> expected.equals(raceViewRepository.findById(expected.getId()).orElse(null))
        );
    }

    @Test
    void updateRaceEvent_updateExistingRaceView() {
        RaceView raceView = RaceViewTestContants.givenRaceView();
        RaceView expected = RaceViewTestContants.givenRaceViewBuilder()
                .name("secondRace")
                .distance("5K")
                .build();
        raceViewRepository.save(raceView);

        assertThat(raceViewRepository.findAll().isEmpty()).isFalse();

        rabbitTemplate.convertAndSend(exchange, routingKey, new EditRaceEvent(UUID.randomUUID(), expected.getId(), expected.getName(), expected.getDistance()));

        await().atMost(5, TimeUnit.SECONDS).until(
                () -> expected.equals(raceViewRepository.findById(raceView.getId()).orElse(null))
        );
    }
}
