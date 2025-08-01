package pl.borovy.personalwebsiteblogapi.model.response;

import java.time.Instant;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserResponse {

    Long id;
    String username;
    String email;
    UserResponse lastModifiedBy;
    Instant createdAt;
    Instant lastModifiedAt;

}
