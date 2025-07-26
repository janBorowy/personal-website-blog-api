package pl.borovy.personalwebsiteblogapi.user;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.borovy.personalwebsiteblogapi.model.LoginRequest;

@RestController
@RequiredArgsConstructor
public class SessionController {

    private static final int TOKEN_EXPIRATION_TIME_IN_HOURS = 3;

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    @Value("${spring.application.name}")
    private String applicationName;

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        var authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getEmail(), loginRequest.getPlainPassword());
        var authentication = authenticationManager.authenticate(authenticationRequest);
        var claims = getClaimsSet(authentication);
        var token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return ResponseEntity.ok(token);
    }

    private JwtClaimsSet getClaimsSet(Authentication authentication) {
        return JwtClaimsSet.builder()
                .issuer(applicationName)
                .expiresAt(Instant.now().plus(TOKEN_EXPIRATION_TIME_IN_HOURS, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("roles", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .build();
    }

}
