package pl.borovy.personalwebsiteblogapi.user;

import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import java.net.URI;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.borovy.personalwebsiteblogapi.Authority;
import pl.borovy.personalwebsiteblogapi.model.User;
import pl.borovy.personalwebsiteblogapi.model.UserAuthority;
import pl.borovy.personalwebsiteblogapi.model.requests.UserRegisterRequest;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserAuthorityRepository userAuthorityRepository;

    public Optional<User> findById(@Nonnull Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User registerUser(@Nonnull UserRegisterRequest request) {
        if (userRepository.existsUserByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with given username exists");
        }

        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with given email exists");
        }

        var user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .encodedPassword(passwordEncoder.encode(request.getPlainPassword()))
                .createdAt(Date.valueOf(LocalDate.now()))
                .enabled(true)
                .build();
        var savedUser = userRepository.save(user);
        userAuthorityRepository.save(new UserAuthority(savedUser.getId(), Authority.USER.scope()));
        return savedUser;
    }

    public URI getLocation(User user) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
    }

}
