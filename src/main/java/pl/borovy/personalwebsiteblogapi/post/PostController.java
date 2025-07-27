package pl.borovy.personalwebsiteblogapi.post;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Operation(summary = "Create a new blog post", security = {@SecurityRequirement(name = "auth", scopes = { "ADMIN" })})
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody SavePostRequest request,
                                                   @Nonnull Authentication authentication) {
        var savedPost = postService.savePost(request, authentication.getName());
        return ResponseEntity.created(postService.getLocation(savedPost))
                .body(PostToPostResponseMapper.INSTANCE.map(savedPost));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a blog post by id")
    public ResponseEntity<PostResponse> getPostById(@PathVariable long id) {
        var response = PostToPostResponseMapper.INSTANCE.map(postService.getPost(id));
        return ResponseEntity.ok(response);
    }

}
