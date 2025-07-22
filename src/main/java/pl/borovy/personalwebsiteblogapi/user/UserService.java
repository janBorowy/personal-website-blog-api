package pl.borovy.personalwebsiteblogapi.user;

import jakarta.annotation.Nonnull;
import java.net.URI;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.borovy.personalwebsiteblogapi.model.User;
import pl.borovy.personalwebsiteblogapi.model.UserRegisterRequest;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findById(@Nonnull Long id) {
        return userRepository.findById(id);
    }

    public User registerUser(@Nonnull UserRegisterRequest request) {

    }

    public URI getURI(User user) {
        return URI.create("http://localhost:8080/user/%d".formatted(user.getId()));
    }

}
