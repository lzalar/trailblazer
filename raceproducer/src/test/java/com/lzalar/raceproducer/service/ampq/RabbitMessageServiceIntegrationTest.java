package com.lzalar.raceproducer.service.ampq;

import com.lzalar.clients.events.race.DeleteRaceEvent;
import com.lzalar.raceproducer.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;


class RabbitMessageServiceIntegrationTest extends BaseIntegrationTest {


    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RabbitMessageService rabbitMessageService;
    @Value("${rabbitmq.queue.name}")
    private String queueName;


    @Test
    void sendRaceEvent_messageIsOnQueue() {
        verifyQueueIsEmpty();
        UUID eventId = UUID.randomUUID();
        UUID raceId = UUID.randomUUID();
        rabbitMessageService.sendMessage(new DeleteRaceEvent(eventId, raceId));
        verifyOneMessageInQueue();
    }


}
