package pl.borovy.personalwebsiteblogapi.user;

import java.io.Serializable;
import lombok.Data;

@Data
public class UserAuthorityPrimaryKey implements Serializable {

    private Long userId;
    private String authority;

}
