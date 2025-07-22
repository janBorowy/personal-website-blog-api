package pl.borovy.personalwebsiteblogapi;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.borovy.personalwebsiteblogapi.model.User;
import pl.borovy.personalwebsiteblogapi.user.UserRepository;

@Configuration
@Profile("dev")
public class DevDataLoader {

    @Bean
    public ApplicationRunner userDataLoader(UserRepository userRepository) {
        return args -> {
            userRepository.save(User.builder()
                            .email("admin@admin.com")
                            .username("admin")
                            .encodedPassword("admin")
                    .build());
        };
    }

}
