package pl.borovy.personalwebsiteblogapi.user;

import org.springframework.data.repository.CrudRepository;
import pl.borovy.personalwebsiteblogapi.model.UserAuthority;
import pl.borovy.personalwebsiteblogapi.model.UserAuthorityPrimaryKey;

public interface UserAuthorityRepository extends CrudRepository<UserAuthority, UserAuthorityPrimaryKey> {}
