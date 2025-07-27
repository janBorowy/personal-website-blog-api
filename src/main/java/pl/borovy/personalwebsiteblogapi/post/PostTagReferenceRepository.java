package pl.borovy.personalwebsiteblogapi.post;

import org.springframework.data.repository.CrudRepository;
import pl.borovy.personalwebsiteblogapi.model.PostTagReference;
import pl.borovy.personalwebsiteblogapi.model.PostTagReferencePrimaryKey;

public interface PostTagReferenceRepository extends CrudRepository<PostTagReference, PostTagReferencePrimaryKey> {

    boolean existsByPostIdAndTagId(long postId, long tagId);
}
