package pl.borovy.personalwebsiteblogapi.post;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.borovy.personalwebsiteblogapi.mappers.PostTagToPostTagResponseMapper;
import pl.borovy.personalwebsiteblogapi.model.requests.AttachATagRequest;
import pl.borovy.personalwebsiteblogapi.model.requests.CreatePostTagRequest;
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
    public PostTagResponse savePostTag(@RequestBody @Valid CreatePostTagRequest request) {
        return PostTagToPostTagResponseMapper.INSTANCE.map(postTagService.createPostTag(request));
    }

    @PostMapping("/attach")
    public ResponseEntity<Void> attachATag(@RequestBody @Valid AttachATagRequest request) {
        postTagService.attachATag(request.getPostId(), request.getTagId());
        return ResponseEntity.ok().build();
    }

}
