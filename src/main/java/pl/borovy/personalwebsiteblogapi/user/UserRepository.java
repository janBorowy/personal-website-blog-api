package pl.borovy.personalwebsiteblogapi.user;

import jakarta.annotation.Nonnull;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import pl.borovy.personalwebsiteblogapi.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsUserByEmail(@Nonnull String email);

    boolean existsUserByUsername(@Nonnull String username);

    Optional<User> findByEmail(@NonNull String email);

    Optional<User> findByUsername(@Nonnull String username);
}
