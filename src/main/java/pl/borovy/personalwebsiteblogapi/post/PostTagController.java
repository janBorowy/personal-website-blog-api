package pl.borovy.personalwebsiteblogapi.post;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.borovy.personalwebsiteblogapi.mappers.PostTagToPostTagResponseMapper;
import pl.borovy.personalwebsiteblogapi.model.requests.AttachATagRequest;
import pl.borovy.personalwebsiteblogapi.model.requests.CreatePostTagRequest;
import pl.borovy.personalwebsiteblogapi.model.requests.DeattachATagRequest;
import pl.borovy.personalwebsiteblogapi.model.response.PostTagResponse;

@RequestMapping("/post-tag")
@RestController
@RequiredArgsConstructor
public class PostTagController {

    private final PostTagService postTagService;

    @GetMapping("/{id}")
    public PostTagResponse getPostTag(@PathVariable long id) {
        return PostTagToPostTagResponseMapper.INSTANCE.map(postTagService.getPostTag(id));
    }

    @PostMapping
    public ResponseEntity<PostTagResponse> savePostTag(@RequestBody @Valid CreatePostTagRequest request) {
        var savedPost = postTagService.createPostTag(request);
        return ResponseEntity.created(postTagService.getLocation(savedPost))
                .body(PostTagToPostTagResponseMapper.INSTANCE.map(savedPost));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a post tag. Deleted post tags are deattached.")
    public ResponseEntity<Void> deletePostTag(@PathVariable long id) {
        postTagService.deletePostTag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/attach")
    public ResponseEntity<Void> attachATag(@RequestBody @Valid AttachATagRequest request) {
        postTagService.attachATag(request.getPostId(), request.getTagId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deattach")
    public ResponseEntity<Void> deattachATag(@RequestBody @Valid DeattachATagRequest request) {
        postTagService.deattachATag(request.getTagId(), request.getTagId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find")
    @Operation(summary = "Find tags containing given searchPhrase in name")
    public ResponseEntity<Page<PostTagResponse>> findTags(@Nonnull @Param("searchPhrase") String searchPhrase, Pageable pageable) {
        var result = postTagService.findTagsUsingSearchPhrase(searchPhrase, pageable)
                .map(PostTagToPostTagResponseMapper.INSTANCE::map);
        return ResponseEntity.ok().body(result);
    }

}
