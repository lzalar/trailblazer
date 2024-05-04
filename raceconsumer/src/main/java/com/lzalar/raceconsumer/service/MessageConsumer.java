package com.lzalar.raceconsumer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@RabbitListener(queues = {"${rabbitmq.queue.name}"})
public class MessageConsumer {

    @RabbitHandler
    public void consume(@Payload String message) {
        log.info("Received message from queue -> {}", message);
    }
}
