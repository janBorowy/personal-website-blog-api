package pl.borovy.personalwebsiteblogapi.model.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreatePostTagRequest {

    @NotBlank
    String name;

    @NotBlank
    String description;

}
