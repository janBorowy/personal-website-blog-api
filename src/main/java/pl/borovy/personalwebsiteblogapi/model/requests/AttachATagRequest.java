package pl.borovy.personalwebsiteblogapi.model.requests;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AttachATagRequest {

    long postId;
    long tagId;

}
