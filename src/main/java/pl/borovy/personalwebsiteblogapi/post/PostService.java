package pl.borovy.personalwebsiteblogapi.post;

import java.net.URI;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.borovy.personalwebsiteblogapi.model.Post;
import pl.borovy.personalwebsiteblogapi.model.requests.SavePostRequest;
import pl.borovy.personalwebsiteblogapi.user.UserRepository;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post savePost(@NonNull SavePostRequest savePostRequest, @NonNull String username) {
        if (postRepository.existsPostByTitle(savePostRequest.getTitle())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post with given title already exists");
        }

        return postRepository.save(
                Post.builder()
                        .author(userRepository.findByUsername(username)
                                .orElseThrow(() -> new IllegalStateException("Principal user does not exist in repository")))
                        .title(savePostRequest.getTitle())
                        .content(savePostRequest.getContent())
                        .build()
        );
    }

    public Post getPost(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No post with given id exists"));
    }

    public URI getLocation(@NonNull Post post) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
    }

}
