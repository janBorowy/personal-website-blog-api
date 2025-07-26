package pl.borovy.personalwebsiteblogapi.user;

import jakarta.annotation.Nonnull;
import java.net.URI;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.borovy.personalwebsiteblogapi.model.User;
import pl.borovy.personalwebsiteblogapi.model.requests.UserRegisterRequest;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public Optional<User> findById(@Nonnull Long id) {
        return userRepository.findById(id);
    }

    public User registerUser(@Nonnull UserRegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .encodedPassword(passwordEncoder.encode(request.getPlainPassword()))
                .createdAt(Date.valueOf(LocalDate.now()))
                .enabled(true)
                .build();
        return userRepository.save(user);
    }

    public URI getLocation(User user) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
    }

}
