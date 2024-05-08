package com.lzalar.raceproducer;


import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSQLSharedContainer extends PostgreSQLContainer<PostgreSQLSharedContainer> {
    private static final String IMAGE_VERSION = "postgres:16.2";
    private static PostgreSQLSharedContainer container;

    private PostgreSQLSharedContainer() {
        super(IMAGE_VERSION);
    }

    public static PostgreSQLSharedContainer getInstance() {
        if (container == null) {
            container = new PostgreSQLSharedContainer();
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
