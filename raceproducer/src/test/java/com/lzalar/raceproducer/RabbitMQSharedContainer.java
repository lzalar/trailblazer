package com.lzalar.raceproducer;


import org.testcontainers.containers.RabbitMQContainer;

import static com.lzalar.raceproducer.BaseIntegrationTest.rabbitMQContainer;


public class RabbitMQSharedContainer extends RabbitMQContainer {
    public static final String RABBITMQ_IMAGE = "rabbitmq:3.13.2-management";
    private static RabbitMQSharedContainer container;

    public static RabbitMQSharedContainer getInstance() {
        if (container == null) {
            container = new RabbitMQSharedContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("spring.rabbitmq.host", rabbitMQContainer.getHost());
        System.setProperty("spring.rabbitmq.port", rabbitMQContainer.getAmqpPort().toString());
        System.setProperty("spring.rabbitmq.username", rabbitMQContainer.getAdminUsername());
        System.setProperty("spring.rabbitmq.password", rabbitMQContainer.getAdminPassword());
    }

    @Override
    public void stop() {
        // Do nothing, JVM handles shut down.
    }
}
