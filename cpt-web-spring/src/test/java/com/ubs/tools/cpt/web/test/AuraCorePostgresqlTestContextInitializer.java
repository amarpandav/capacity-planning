package com.ubs.tools.cpt.web.test;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

public class AuraCorePostgresqlTestContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @SuppressWarnings("resource")
    private final static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16")
        .withDatabaseName("auraCore")
        .withUsername("auraCore")
        .withPassword("auraCore");


    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
        startContainer();
        setProperties();
    }

    private static void startContainer() {
        if (!container.isRunning()) {
            container.start();
        }
    }

    private static void setProperties() {
        Map.of(
            "aura.datasource.url", container.getJdbcUrl(),
            "aura.datasource.username", container.getUsername(),
            "aura.datasource.password", container.getPassword(),
            "aura.datasource.driver-class-name", container.getDriverClassName()
        ).forEach(System::setProperty);
    }
}
