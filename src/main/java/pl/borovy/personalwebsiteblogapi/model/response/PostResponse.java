package pl.borovy.personalwebsiteblogapi.model.response;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class PostResponse extends AuditableResponse {

    Long id;
    String title;
    String content;
    UserResponse author;

}
