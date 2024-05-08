package com.lzalar.raceconsumer.service.projector;

import com.lzalar.clients.events.race.DeleteRaceEvent;
import com.lzalar.clients.events.race.EditRaceEvent;
import com.lzalar.clients.events.raceapplication.CreateRaceApplicationEvent;
import com.lzalar.clients.events.raceapplication.DeleteRaceApplicationEvent;
import com.lzalar.raceconsumer.BaseIntegrationTest;
import com.lzalar.raceconsumer.domain.RaceApplicationView;
import com.lzalar.raceconsumer.domain.RaceView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.lzalar.raceconsumer.constants.RaceApplicationViewTestContants.givenRaceApplicationView;
import static com.lzalar.raceconsumer.constants.RaceApplicationViewTestContants.givenRaceApplicationViewBuilder;
import static com.lzalar.raceconsumer.constants.RaceViewTestContants.givenRaceViewBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@ExtendWith(MockitoExtension.class)
class RaceApplicationViewProjectorIntegrationTest extends BaseIntegrationTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    void deleteRaceEvent_deleteRaceApplicationViews() {
        RaceApplicationView raceApplicationView = givenRaceApplicationView();
        raceApplicationViewRepository.save(raceApplicationView);

        assertThat(raceApplicationViewRepository.findAll().isEmpty()).isFalse();

        rabbitTemplate.convertAndSend(exchange, routingKey, new DeleteRaceEvent(UUID.randomUUID(), raceApplicationView.getRaceId()));

        await().atMost(5, TimeUnit.SECONDS).until(() -> raceApplicationViewRepository.findAll().isEmpty());
    }

    @Test
    void updateRaceEvent_updateExistingRaceApplicationView() {
        RaceApplicationView applicationView = givenRaceApplicationView();
        raceApplicationViewRepository.save(applicationView);

        RaceView raceView = givenRaceViewBuilder()
                .name("secondRace")
                .distance("5K")
                .build();

        RaceApplicationView expected = givenRaceApplicationViewBuilder()
                .raceName(raceView.getName())
                .distance(raceView.getDistance())
                .build();

        assertThat(raceApplicationViewRepository.findAll().isEmpty()).isFalse();

        rabbitTemplate.convertAndSend(exchange, routingKey, new EditRaceEvent(UUID.randomUUID(), raceView.getId(), raceView.getName(), raceView.getDistance()));

        await().atMost(5, TimeUnit.SECONDS).until(
                () -> expected.equals(raceApplicationViewRepository.findById(applicationView.getRaceApplicationId()).orElse(null))
        );
    }

    @Test
    void createRaceApplicationEvent_createRaceApplicationView() {
        RaceApplicationView expected = givenRaceApplicationView();

        assertThat(raceApplicationViewRepository.findAll().isEmpty()).isTrue();

        rabbitTemplate.convertAndSend(exchange, routingKey, new CreateRaceApplicationEvent(UUID.randomUUID(),
                expected.getRaceApplicationId(), expected.getFirstName(),
                expected.getLastName(),expected.getClub(),expected.getRaceId(),
                expected.getRaceName(),expected.getDistance(),expected.getUserId()));

        await().atMost(5, TimeUnit.SECONDS).until(
                () -> expected.equals(raceApplicationViewRepository.findById(expected.getRaceApplicationId()).orElse(null))
        );
    }

    @Test
    void deleteRaceApplicationEvent_deleteRaceApplicationView() {
        RaceApplicationView raceApplicationView = givenRaceApplicationView();
        raceApplicationViewRepository.save(raceApplicationView);

        assertThat(raceApplicationViewRepository.findAll().isEmpty()).isFalse();

        rabbitTemplate.convertAndSend(exchange, routingKey, new DeleteRaceApplicationEvent(UUID.randomUUID(), raceApplicationView.getRaceApplicationId()));

        await().atMost(5, TimeUnit.SECONDS).until(() -> raceApplicationViewRepository.findAll().isEmpty());
    }
}
