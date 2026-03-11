package com.ruben.lotr.testsupport;

import org.testcontainers.containers.MySQLContainer;

final class MySqlTestContainerSingleton {

    private static final MySQLContainer<?> INSTANCE = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("lotr_test")
            .withUsername("test")
            .withPassword("test");

    static {
        INSTANCE.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                INSTANCE.stop();
            } catch (Exception ignored) {
                // best-effort cleanup
            }
        }));
    }

    private MySqlTestContainerSingleton() {
    }

    static MySQLContainer<?> get() {
        if (!INSTANCE.isRunning()) {
            INSTANCE.start();
        }
        return INSTANCE;
    }
}
