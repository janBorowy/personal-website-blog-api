package pl.borovy.personalwebsiteblogapi.post;


import jakarta.annotation.Nonnull;
import org.springframework.data.repository.CrudRepository;
import pl.borovy.personalwebsiteblogapi.model.Post;

public interface PostRepository extends CrudRepository<Post, Long> {

    boolean existsPostByTitle(@Nonnull String title);
}
