package pl.borovy.personalwebsiteblogapi.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserResponse {

    private Long id;
    private String username;
    private String email;

}
