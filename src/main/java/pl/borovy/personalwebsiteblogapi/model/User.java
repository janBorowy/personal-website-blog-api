package pl.borovy.personalwebsiteblogapi.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Date;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import pl.borovy.personalwebsiteblogapi.user.UserAuthority;

@Data
@Builder
@Entity
@Table(name = "REGISTERED_USER")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    private String username;
    @Column(nullable = false)
    private boolean enabled;
    @Column(nullable = false, unique = true, length = 256)
    private String email;
    @Column(nullable = false, length = 72)
    private String encodedPassword;
    @Column(nullable = false)
    private Date createdAt;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "USER_ID")
    private List<UserAuthority> authorities;

    @Override
    public String getPassword() {
        return getEncodedPassword();
    }
}
