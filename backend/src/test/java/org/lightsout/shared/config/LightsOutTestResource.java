package org.lightsout.shared.config;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

public class LightsOutTestResource implements QuarkusTestResourceLifecycleManager {

    private static final String POSTGRES_IMAGE_NAME = "postgres:latest";
    private static final String DB_NAME = "lights_out_db_test";
    private static final String DB_USER = "p0stgres";
    private static final String DB_PWD = "p0stgres";

    private PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>(POSTGRES_IMAGE_NAME)
                    .withDatabaseName(DB_NAME)
                    .withUsername(DB_USER)
                    .withPassword(DB_PWD);

    @Override
    public Map<String, String> start() {
        postgreSQLContainer.start();
        return Map.of(
                "quarkus.datasource.username", postgreSQLContainer.getUsername(),
                "quarkus.datasource.password", postgreSQLContainer.getPassword(),
                "quarkus.datasource.jdbc.url", postgreSQLContainer.getJdbcUrl()
        );
    }

    @Override
    public void stop() {
        postgreSQLContainer.stop();
    }
}
