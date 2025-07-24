package pl.borovy.personalwebsiteblogapi.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.borovy.personalwebsiteblogapi.model.User;
import pl.borovy.personalwebsiteblogapi.model.UserRegisterRequest;

@RestController
@RequestMapping(value = "/user", produces = "application/json")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") Long id) {
        return userService.findById(id)
                .map(this::userToResponse)
                .map(it -> ResponseEntity.ok().body(it))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRegisterRequest request) {
        var registeredUser = userService.registerUser(request);
        return ResponseEntity.created(userService.getURI(registeredUser))
                .body(userToResponse(registeredUser));
    }

    private UserResponse userToResponse(User user) {
        assert user != null;

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

}
