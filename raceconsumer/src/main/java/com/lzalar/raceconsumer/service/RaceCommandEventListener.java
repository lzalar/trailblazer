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

@Slf4j
@Component
@RequiredArgsConstructor
@RabbitListener(queues = {"${rabbitmq.queue.name}"})
public class RaceCommandEventListener {

    private final List<Projector> projectors;

    @RabbitHandler
    public void consume(ApplicationEvent message) {
        log.info("Received message from queue -> {}", message);
        // todo save event and check if it was already processed
        projectors.forEach(projector -> projector.process(message));

        log.info("Processed event with id: {}", message.getEventId());
    }
}
