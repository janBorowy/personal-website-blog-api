package pl.borovy.personalwebsiteblogapi.model.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

@Value
public class LoginRequest {

    @NotBlank
    @Length(min = 8, max = 64, message = "")
    String username;

    @NotBlank
    @Length(min = 8, max = 64, message = "Password must be between 8 to 64 characters")
    String plainPassword;

}
