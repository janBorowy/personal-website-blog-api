package pl.borovy.personalwebsiteblogapi.user;

import org.springframework.data.repository.CrudRepository;
import pl.borovy.personalwebsiteblogapi.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
