package pl.borovy.personalwebsiteblogapi.post;

import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.borovy.personalwebsiteblogapi.model.PostTag;

public interface PostTagRepository extends CrudRepository<PostTag, Long> {

    boolean existsPostTagById(@Nonnull Long id);

    boolean existsPostTagByName(@Nonnull String name);

    @Query("select pt from PostTag pt where pt.name ilike %?1% order by pt.name asc")
    Page<PostTag> findByName(String name, Pageable pageable);
}
