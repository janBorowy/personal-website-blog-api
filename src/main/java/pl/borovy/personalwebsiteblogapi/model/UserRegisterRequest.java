package pl.borovy.personalwebsiteblogapi.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import org.hibernate.validator.constraints.Length;
import pl.borovy.personalwebsiteblogapi.validation.UniqueEmail;

@Value
public class UserRegisterRequest {

    @NotBlank
    @Length(min = 8, max = 64, message = "Username's length must be between 8 to 64 characters")
    String username;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email can't be blank")
    @UniqueEmail(message = "User with given email already exists")
    String email;
    @Length(min = 8, max = 64, message = "Password must be between 8 to 64 characters")
    String plainPassword;

}
