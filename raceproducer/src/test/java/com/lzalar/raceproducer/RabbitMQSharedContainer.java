package com.lzalar.raceproducer;


import org.testcontainers.containers.RabbitMQContainer;


public class RabbitMQSharedContainer extends RabbitMQContainer {
    public static final String RABBITMQ_IMAGE = "rabbitmq:3.13.2-management";
    private static RabbitMQSharedContainer container;

    public RabbitMQSharedContainer(){
        super(RABBITMQ_IMAGE);
    }

    public static RabbitMQSharedContainer getInstance() {
        if (container == null) {
            container = new RabbitMQSharedContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("spring.rabbitmq.host", container.getHost());
        System.setProperty("spring.rabbitmq.port", container.getAmqpPort().toString());
        System.setProperty("spring.rabbitmq.username", container.getAdminUsername());
        System.setProperty("spring.rabbitmq.password", container.getAdminPassword());
    }

    @Override
    public void stop() {
        // Do nothing, JVM handles shut down.
    }
}
