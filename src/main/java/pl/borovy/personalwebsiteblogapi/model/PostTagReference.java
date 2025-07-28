package pl.borovy.personalwebsiteblogapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Entity
@IdClass(PostTagReferencePrimaryKey.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class PostTagReference {

    public PostTagReference(long postId, long tagId) {
        this.postId = postId;
        this.tagId = tagId;
        this.post = Post.builder().id(postId).build();
        this.tag = PostTag.builder().id(tagId).build();
    }

    @Id
    Long postId;

    @Id
    Long tagId;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "POST_ID")
    Post post;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "TAG_ID")
    PostTag tag;

}
