package pl.borovy.personalwebsiteblogapi.model.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

@Value
@Builder
public class UserRegisterRequest {

    @NotBlank
    @Length(min = 5, max = 64, message = "Username's length must be between 5 to 64 characters")
    String username;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email can't be blank")
    String email;
    @NotBlank
    @Length(min = 8, max = 64, message = "Password must be between 8 to 64 characters")
    String plainPassword;

}
