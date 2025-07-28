package pl.borovy.personalwebsiteblogapi.model.requests;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeattachATagRequest {

    long postId;
    long tagId;

}
