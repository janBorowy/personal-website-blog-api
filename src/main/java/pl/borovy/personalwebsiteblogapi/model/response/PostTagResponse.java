package pl.borovy.personalwebsiteblogapi.model.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PostTagResponse {

    Long id;
    String name;
    String description;

}
