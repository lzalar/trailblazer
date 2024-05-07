package com.lzalar.raceconsumer.service;

import com.lzalar.clients.events.race.CreateRace;
import com.lzalar.clients.events.race.DeleteRace;
import com.lzalar.clients.events.race.EditRace;
import com.lzalar.clients.events.race.application.CreateRaceApplication;
import com.lzalar.clients.events.race.application.DeleteRaceApplication;
import com.lzalar.raceconsumer.domain.RaceApplicationPerUser;
import com.lzalar.raceconsumer.domain.RaceApplicationBasic;
import com.lzalar.raceconsumer.domain.RaceView;
import com.lzalar.raceconsumer.repository.RaceApplicationPerUserRepository;
import com.lzalar.raceconsumer.repository.RaceViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@RabbitListener(queues = {"${rabbitmq.queue.name}"})
public class MessageConsumer {

    private final RaceViewRepository raceViewRepository;
    private final RaceApplicationPerUserRepository raceApplicationPerUserRepository;

    @RabbitHandler
    public void consume(CreateRace message) {
        log.info("Received message from queue -> {}", message);
        raceViewRepository.save(new RaceView(message.uuid(),message.name(),message.distance()));
    }

    @RabbitHandler
    public void consume(EditRace message) {
        log.info("Received message from queue -> {}", message);
    }

    @RabbitHandler
    public void consume(DeleteRace message) {
        log.info("Received message from queue -> {}", message);
    }

    @RabbitHandler
    public void consume(CreateRaceApplication message) {
        log.info("Received message from queue -> {}", message);



        Optional<RaceApplicationPerUser> appliedRacesPerUserOptional = raceApplicationPerUserRepository.findById(message.userId());

        if (appliedRacesPerUserOptional.isEmpty()){
            raceApplicationPerUserRepository.save(RaceApplicationPerUser.builder()
                            .userId(message.userId())
                            .raceApplications(List.of(
                                    new RaceApplicationBasic(
                                            message.id(),
                                            message.club(),
                                            message.raceId(),
                                            null,
                                            null
                                    )
                            ))
                    .build());
        } else {
            appliedRacesPerUserOptional.get()
                    .getRaceApplications()
                    .add(new RaceApplicationBasic(
                            message.id(),
                            message.club(),
                            message.raceId(),
                            null,
                            null
                    ));
            raceApplicationPerUserRepository.save(appliedRacesPerUserOptional.get());
        }
    }

    @RabbitHandler
    public void consume(DeleteRaceApplication message) {
        log.info("Received message from queue -> {}", message);
    }
}
