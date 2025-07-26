package pl.borovy.personalwebsiteblogapi.model;

import java.sql.Date;
import lombok.Builder;
import lombok.Value;
import pl.borovy.personalwebsiteblogapi.user.UserResponse;

@Value
@Builder
public class PostResponse {

    Long id;
    String title;
    String content;
    UserResponse author;
    Date createdAt;

}
