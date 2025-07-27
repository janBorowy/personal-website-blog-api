package pl.borovy.personalwebsiteblogapi.post;

import jakarta.annotation.Nonnull;
import org.springframework.data.repository.CrudRepository;
import pl.borovy.personalwebsiteblogapi.model.PostTag;

public interface PostTagRepository extends CrudRepository<PostTag, Long> {
    boolean existsPostTagById(@Nonnull Long id);
    boolean existsPostTagByName(@Nonnull String name);
}
