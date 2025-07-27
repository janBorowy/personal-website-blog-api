package pl.borovy.personalwebsiteblogapi.post;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.borovy.personalwebsiteblogapi.mappers.PostToPostResponseMapper;
import pl.borovy.personalwebsiteblogapi.model.PostResponse;
import pl.borovy.personalwebsiteblogapi.model.requests.SavePostRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    @Operation(security = { @SecurityRequirement(name = "auth") })
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody SavePostRequest request,
                                                   @Nonnull Authentication authentication) {
        var savedPost = postService.savePost(request, authentication.getName());
        return ResponseEntity.created(postService.getLocation(savedPost))
                .body(PostToPostResponseMapper.INSTANCE.map(savedPost));
    }

}
