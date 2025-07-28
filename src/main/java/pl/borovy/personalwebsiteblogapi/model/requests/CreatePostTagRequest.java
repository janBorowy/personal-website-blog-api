package pl.borovy.personalwebsiteblogapi.model.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

@Value
@Builder
public class CreatePostTagRequest {

    @NotBlank
    @Length(max = 64)
    String name;

    @NotBlank
    @Length(max = 256)
    String description;

}
