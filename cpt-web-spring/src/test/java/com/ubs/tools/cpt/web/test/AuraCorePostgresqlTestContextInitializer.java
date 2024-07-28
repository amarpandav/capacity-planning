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
            aura("url"), container.getJdbcUrl(),
            aura("username"), container.getUsername(),
            aura("password"), container.getPassword(),
            aura("driver-class-name"), container.getDriverClassName()
        ).forEach(System::setProperty);
    }
    private static String aura(String key) {
        return "aura.datasource." + key;
    }
}
