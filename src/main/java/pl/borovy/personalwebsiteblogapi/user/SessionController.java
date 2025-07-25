package pl.borovy.personalwebsiteblogapi.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.borovy.personalwebsiteblogapi.model.LoginRequest;

@RestController
@RequiredArgsConstructor
public class SessionController {

    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
        var authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getEmail(), loginRequest.getPlainPassword());
        authenticationManager.authenticate(authenticationRequest);
        return ResponseEntity.ok().build();
    }

}
