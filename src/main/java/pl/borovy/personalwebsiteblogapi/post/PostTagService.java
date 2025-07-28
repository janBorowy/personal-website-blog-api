package pl.borovy.personalwebsiteblogapi.post;

import jakarta.transaction.Transactional;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.borovy.personalwebsiteblogapi.model.Post;
import pl.borovy.personalwebsiteblogapi.model.PostTag;
import pl.borovy.personalwebsiteblogapi.model.PostTagReference;
import pl.borovy.personalwebsiteblogapi.model.PostTagReferencePrimaryKey;
import pl.borovy.personalwebsiteblogapi.model.requests.CreatePostTagRequest;

@Service
@RequiredArgsConstructor
public class PostTagService {

    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;
    private final PostTagReferenceRepository postTagReferenceRepository;

    public PostTag getPostTag(long id) {
        return postTagRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public PostTag createPostTag(CreatePostTagRequest request) {
        if (postTagRepository.existsPostTagByName(request.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tag with given name already exists");
        }

        return postTagRepository.save(PostTag.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build());
    }

    public void deletePostTag(long tagId) {
        postTagRepository.deleteById(tagId);
    }

    @Transactional
    public void attachATag(long postId, long tagId) {
        var post = fetchPostByIdOrThrow(postId);
        var tag = fetchTagByIdOrThrow(tagId);

        if (postTagReferenceRepository.existsByPostIdAndTagId(postId, tagId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tag already attached");
        }

        postTagReferenceRepository.save(PostTagReference.builder()
                .post(post)
                .tag(tag)
                .build());
    }

    @Transactional
    public void deattachATag(long postId, long tagId) {
        if (!postRepository.existsPostById(postId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post with given id does not exist");
        }
        if (!postTagRepository.existsPostTagById(tagId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tag with given id does not exist");
        }

        var reference = postTagReferenceRepository.findById(new PostTagReferencePrimaryKey(postId, tagId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tag not attached"));
        postTagReferenceRepository.delete(reference);
    }

    public URI getLocation(PostTag postTag) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(postTag.getId())
                .toUri();
    }

    private Post fetchPostByIdOrThrow(long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post with given id does not exist"));
    }

    private PostTag fetchTagByIdOrThrow(long tagId) {
        return postTagRepository.findById(tagId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tag with given id does not exist"));
    }

}
