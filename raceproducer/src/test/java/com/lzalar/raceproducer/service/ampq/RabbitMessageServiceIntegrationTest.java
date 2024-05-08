package com.lzalar.raceproducer.service.ampq;

import com.lzalar.clients.events.race.DeleteRaceEvent;
import com.lzalar.raceproducer.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


class RabbitMessageServiceIntegrationTest extends BaseIntegrationTest {


    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RabbitMessageService rabbitMessageService;

    @Test
    void sendRaceEvent_messageIsOnQueue() {
        verifyQueueIsEmpty();
        UUID eventId = UUID.randomUUID();
        UUID raceId = UUID.randomUUID();
        rabbitMessageService.sendMessage(new DeleteRaceEvent(eventId, raceId));
        verifyOneMessageInQueue();
    }


}
