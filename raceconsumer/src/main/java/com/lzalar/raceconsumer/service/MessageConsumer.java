package com.lzalar.raceconsumer.service;

import com.lzalar.clients.events.race.CreateRace;
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

    @RabbitHandler
    public void consume(CreateRace message) {
        log.info("Received message from queue -> {}", message);
    }
}
