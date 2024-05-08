package com.lzalar.raceproducer.service.ampq;

import com.lzalar.clients.events.race.DeleteRaceEvent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class RabbitMessageServiceIntegrationTest {

    public static final String RABBITMQ_IMAGE = "rabbitmq:latest";

    @Container
    static RabbitMQContainer container = new RabbitMQContainer(RABBITMQ_IMAGE);

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", container::getHost);
        registry.add("spring.rabbitmq.port", container::getAmqpPort);
        registry.add("spring.rabbitmq.username", container::getAdminUsername);
        registry.add("spring.rabbitmq.password", container::getAdminPassword);
    }


    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RabbitAdmin rabbitAdmin;
    @Autowired
    RabbitListenerEndpointRegistry listenerEndpointRegistry;
    @Autowired
    RabbitMessageService rabbitMessageService;
    @Value("${rabbitmq.queue.name}")
    private String queueName;


    @Test
    void onPaymentEvent_test() {
        Assertions.assertThat(rabbitAdmin.getQueueInfo(queueName).getMessageCount()).isEqualTo(0);
        UUID eventId = UUID.randomUUID();
        UUID raceId = UUID.randomUUID();
        rabbitMessageService.sendMessage(new DeleteRaceEvent(eventId, raceId));

        Awaitility.await().atMost(5, TimeUnit.SECONDS)
                        .until(verifyMessageInQueue(), Boolean.TRUE::equals);
    }

    private Callable<Boolean> verifyMessageInQueue() {
        return ()->rabbitAdmin.getQueueInfo(queueName).getMessageCount() == 1;
    }


}
