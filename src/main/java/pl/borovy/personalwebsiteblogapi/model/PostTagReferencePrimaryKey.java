package pl.borovy.personalwebsiteblogapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostTagReferencePrimaryKey {

    private Long postId;
    private Long tagId;

}
