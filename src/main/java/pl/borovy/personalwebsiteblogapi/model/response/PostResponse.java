package pl.borovy.personalwebsiteblogapi.model.response;

import java.sql.Date;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PostResponse {

    Long id;
    String title;
    String content;
    UserResponse author;
    Date createdAt;

}
