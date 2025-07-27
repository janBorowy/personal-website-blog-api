package pl.borovy.personalwebsiteblogapi.model;

import lombok.Data;

@Data
public class PostTagReferencePrimaryKey {

    private Long postId;
    private Long tagId;

}
