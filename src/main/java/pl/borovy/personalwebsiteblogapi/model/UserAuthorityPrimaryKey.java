package pl.borovy.personalwebsiteblogapi.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class UserAuthorityPrimaryKey implements Serializable {

    private Long userId;
    private String authority;

}
