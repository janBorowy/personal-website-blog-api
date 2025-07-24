package pl.borovy.personalwebsiteblogapi.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import org.springframework.data.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import pl.borovy.personalwebsiteblogapi.model.User;

@Data
@Entity
public class UserAuthority implements GrantedAuthority {

    @Column(nullable = false)
    @Reference(to = User.class)
    private String userId;
    @Column(nullable = false, length = 64)
    private String authority;

}
