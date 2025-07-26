package pl.borovy.personalwebsiteblogapi.post;

import java.net.URI;
import java.sql.Date;
import java.time.LocalDate;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
        return postRepository.save(
                Post.builder()
                        .author(userRepository.findByUsername(username)
                                .orElseThrow(() -> new IllegalStateException("Principal user does not exist in repository")))
                        .title(savePostRequest.getTitle())
                        .content(savePostRequest.getContent())
                        .createdAt(Date.valueOf(LocalDate.now()))
                        .build()
        );
    }

    public URI getLocation(@NonNull Post post) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
    }

}
