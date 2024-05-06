package com.lzalar.raceconsumer.service;

import com.lzalar.clients.events.race.CreateRace;
import com.lzalar.clients.events.race.DeleteRace;
import com.lzalar.clients.events.race.EditRace;
import com.lzalar.clients.events.race.application.CreateRaceApplication;
import com.lzalar.clients.events.race.application.DeleteRaceApplication;
import com.lzalar.raceconsumer.domain.RaceView;
import com.lzalar.raceconsumer.repository.RaceViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@RabbitListener(queues = {"${rabbitmq.queue.name}"})
public class MessageConsumer {

    private final RaceViewRepository raceViewRepository;

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
    }

    @RabbitHandler
    public void consume(DeleteRaceApplication message) {
        log.info("Received message from queue -> {}", message);
    }
}
