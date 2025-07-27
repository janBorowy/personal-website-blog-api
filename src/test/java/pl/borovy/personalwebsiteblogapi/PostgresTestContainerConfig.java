package pl.borovy.personalwebsiteblogapi;

import static pl.borovy.personalwebsiteblogapi.StaticTestObjects.ADMIN;
import static pl.borovy.personalwebsiteblogapi.StaticTestObjects.USER;

import java.util.List;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pl.borovy.personalwebsiteblogapi.model.UserAuthority;
import pl.borovy.personalwebsiteblogapi.user.UserRepository;

@TestConfiguration
@Testcontainers
public class PostgresTestContainerConfig {

    @Container
    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
            .withDatabaseName("jb-blog");

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSqlContainer() {
        return POSTGRE_SQL_CONTAINER;
    }

    @Bean
    public ApplicationRunner loadTestData(UserRepository userRepository) {
        return args -> {
            var savedAdmin = userRepository.save(ADMIN);
            savedAdmin.setAuthorities(List.of(
                    new UserAuthority(savedAdmin.getId(), "SCOPE_ADMIN")
            ));
            userRepository.save(savedAdmin);
            var savedUser = userRepository.save(USER);
            savedUser.setAuthorities(List.of(
                    new UserAuthority(savedUser.getId(), "SCOPE_USER")
            ));
            userRepository.save(savedUser);
        };
    }

}
