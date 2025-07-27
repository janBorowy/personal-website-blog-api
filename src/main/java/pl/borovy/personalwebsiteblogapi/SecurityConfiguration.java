package pl.borovy.personalwebsiteblogapi;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final String ENCODE_ID = "bcrypt";
    private static final int BCRYPT_ENCRYPTION_STRENGTH = 8;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // TODO: enable csrf and make it work with swagger
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeHttpRequests -> {
                    authorizeHttpRequests.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    authorizeHttpRequests.requestMatchers(HttpMethod.POST, "/user/register").permitAll();
                    authorizeHttpRequests.requestMatchers(HttpMethod.POST, "/post").hasAuthority(Authority.ADMIN.scope());
                    authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/post/*").permitAll();
                    authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/post-tag/*").permitAll();
                    authorizeHttpRequests.requestMatchers(HttpMethod.POST, "/post-tag", "/post-tag/attach").hasAuthority(Authority.ADMIN.scope());
                    authorizeHttpRequests.requestMatchers("/error/**").permitAll();
                    authorizeHttpRequests.requestMatchers("/swagger-ui.html", "/swagger-ui/**").permitAll();
                    authorizeHttpRequests.requestMatchers("/v3/api-docs", "/v3/api-docs/**").permitAll();
                    authorizeHttpRequests.anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(Customizer.withDefaults()))
                .logout(logout ->
                        logout.permitAll()
                                .logoutSuccessHandler((request, response, authentication) ->
                                    response.setStatus(HttpServletResponse.SC_OK)
                                ));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new DelegatingPasswordEncoder(ENCODE_ID, Map.of(
                ENCODE_ID, new BCryptPasswordEncoder(BCRYPT_ENCRYPTION_STRENGTH)
        ));
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

}
