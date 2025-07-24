package pl.borovy.personalwebsiteblogapi;

import java.sql.Date;
import java.time.LocalDate;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.borovy.personalwebsiteblogapi.model.User;
import pl.borovy.personalwebsiteblogapi.user.UserRepository;

@Configuration
@Profile("dev")
public class DevDataLoader {

    @Bean
    public ApplicationRunner userDataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            userRepository.save(User.builder()
                            .email("admin@admin.com")
                            .username("admin")
                            .encodedPassword(passwordEncoder.encode("admin"))
                            .createdAt(Date.valueOf(LocalDate.now()))
                    .build());
        };
    }

}
