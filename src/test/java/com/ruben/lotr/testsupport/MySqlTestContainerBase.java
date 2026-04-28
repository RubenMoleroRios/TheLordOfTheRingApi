package com.ruben.lotr.testsupport;

import java.util.UUID;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public abstract class MySqlTestContainerBase {

    @DynamicPropertySource
    static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        MySQLContainer<?> mysql = MySqlTestContainerSingleton.get();
        String databaseName = "lotr_test_" + UUID.randomUUID().toString().replace("-", "");
        String baseJdbcUrl = mysql.getJdbcUrl().replace(
                "/" + mysql.getDatabaseName(),
                "/" + databaseName);
        final String jdbcUrl = baseJdbcUrl
                + (baseJdbcUrl.contains("?") ? "&" : "?")
                + "createDatabaseIfNotExist=true";

        registry.add("spring.datasource.url", () -> jdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.datasource.driver-class-name", mysql::getDriverClassName);

        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.MySQLDialect");

        registry.add("spring.flyway.enabled", () -> "true");
        registry.add("spring.flyway.locations", () -> "classpath:db/test-migration");
        registry.add("spring.flyway.clean-disabled", () -> "false");
    }

    @Autowired(required = false)
    private Flyway flyway;

    @BeforeEach
    void resetDatabase() {
        if (flyway == null) {
            return;
        }

        flyway.clean();
        flyway.migrate();
    }
}
