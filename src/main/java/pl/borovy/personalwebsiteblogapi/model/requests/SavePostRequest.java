package pl.borovy.personalwebsiteblogapi.model.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import org.hibernate.validator.constraints.Length;
import pl.borovy.personalwebsiteblogapi.model.validation.UniqueTitle;

@Value
public class SavePostRequest {

    @NotBlank
    @Length(min = 8, max = 256, message = "Title's length must be between 8 to 256 characters")
    @UniqueTitle
    String title;

    @NotBlank
    String content;

}
