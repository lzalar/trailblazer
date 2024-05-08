package com.lzalar.raceproducer;


import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSQLCustomContainer extends PostgreSQLContainer<PostgreSQLCustomContainer> {
    private static final String IMAGE_VERSION = "postgres:16.2";
    private static PostgreSQLCustomContainer container;

    private PostgreSQLCustomContainer() {
        super(IMAGE_VERSION);
    }

    public static PostgreSQLCustomContainer getInstance() {
        if (container == null) {
            container = new PostgreSQLCustomContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());
    }

    @Override
    public void stop() {
        // Do nothing, JVM handles shut down.
    }
}
