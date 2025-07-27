package pl.borovy.personalwebsiteblogapi.post;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.borovy.personalwebsiteblogapi.model.PostTag;
import pl.borovy.personalwebsiteblogapi.model.PostTagReference;
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

    @Transactional
    public void attachATag(long postId, long tagId) {
        if (!postRepository.existsPostById(postId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post with given id does not exist");
        }
        if (!postTagRepository.existsPostTagById(tagId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tag with given id does not exist");
        }
        if (postTagReferenceRepository.existsByPostIdAndTagId(postId, tagId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tag already attached");
        }

        postTagReferenceRepository.save(PostTagReference.builder()
                .postId(postId)
                .tagId(tagId)
                .build());
    }

}
