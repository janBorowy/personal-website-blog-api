package pl.borovy.personalwebsiteblogapi;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final String ENCODE_ID = "bcrypt";
    private static final int BCRYPT_ENCRYPTION_STRENGTH = 16;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // TODO: configure security rules
        http.csrf(AbstractHttpConfigurer::disable);
        http.headers(httpSecurityHeadersConfigurer ->
                httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin
                    )
        );
        return http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.anyRequest().permitAll()).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new DelegatingPasswordEncoder(ENCODE_ID, Map.of(
                ENCODE_ID, new BCryptPasswordEncoder(BCRYPT_ENCRYPTION_STRENGTH)
        ));
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }
}
