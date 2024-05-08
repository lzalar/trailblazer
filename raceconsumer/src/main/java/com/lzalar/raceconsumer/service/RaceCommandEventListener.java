package com.lzalar.raceconsumer.service;

import com.lzalar.clients.events.ApplicationEvent;
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

        projectors.forEach(projector -> projector.process(message));

        log.info("Processed event with id: {}", message.getEventId());
    }
}
