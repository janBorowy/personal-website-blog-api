package pl.borovy.personalwebsiteblogapi.model.requests;

import lombok.Value;

@Value
public class AttachATagRequest {

    long postId;
    long tagId;

}
