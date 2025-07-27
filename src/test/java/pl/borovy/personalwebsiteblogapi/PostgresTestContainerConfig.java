package pl.borovy.personalwebsiteblogapi;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
@Testcontainers
public class PostgresTestContainerConfig {

    @Container
    private static final PostgreSQLContainer<?> POSTGRES_SQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
            .withDatabaseName("jb-blog");

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgresSqlContainer() {
        return POSTGRES_SQL_CONTAINER;
    }

}
