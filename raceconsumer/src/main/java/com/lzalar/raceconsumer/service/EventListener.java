package com.lzalar.raceconsumer.service;

import com.lzalar.clients.events.race.ApplicationEvent;
import com.lzalar.clients.events.race.CreateRaceEvent;
import com.lzalar.clients.events.race.DeleteRaceEvent;
import com.lzalar.clients.events.race.EditRaceEvent;
import com.lzalar.clients.events.race.application.CreateRaceApplicationEvent;
import com.lzalar.clients.events.race.application.DeleteRaceApplicationEvent;
import com.lzalar.raceconsumer.domain.RaceApplicationBasic;
import com.lzalar.raceconsumer.domain.RaceApplicationPerUserProjection;
import com.lzalar.raceconsumer.domain.RaceViewProjection;
import com.lzalar.raceconsumer.repository.RaceApplicationPerUserRepository;
import com.lzalar.raceconsumer.repository.RaceViewRepository;
import com.lzalar.raceconsumer.service.projector.Projector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@RabbitListener(queues = {"${rabbitmq.queue.name}"})
public class EventListener {

    private final RaceViewRepository raceViewRepository;
    private final RaceA
    private final RaceApplicationPerUserRepository raceApplicationPerUserRepository;
    private final List<Projector> projectors;

    @RabbitHandler
    public void consume(ApplicationEvent message) {
        log.info("Received message from queue -> {}", message);

        if (message instanceof CreateRaceEvent createRaceEventMessage){
            log.info("Received message from queue -> {}", createRaceEventMessage);
            raceViewRepository.save(new RaceViewProjection(createRaceEventMessage.getRaceId(), createRaceEventMessage.getName(), createRaceEventMessage.getDistance()));
        } else {
            log.info("didn't manage to do anything");
        }


    }

    @RabbitHandler
    public void consume(EditRaceEvent message) {
        log.info("Received message from queue -> {}", message);
    }

    @RabbitHandler
    public void consume(DeleteRaceEvent message) {
        log.info("Received message from queue -> {}", message);
    }

    @RabbitHandler
    public void consume(CreateRaceApplicationEvent message) {
        log.info("Received message from queue -> {}", message);



        Optional<RaceApplicationPerUserProjection> appliedRacesPerUserOptional = raceApplicationPerUserRepository.findById(message.userId());

        if (appliedRacesPerUserOptional.isEmpty()){
            raceApplicationPerUserRepository.save(RaceApplicationPerUserProjection.builder()
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
    public void consume(DeleteRaceApplicationEvent message) {
        log.info("Received message from queue -> {}", message);
    }
}
