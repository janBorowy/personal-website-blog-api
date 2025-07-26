package pl.borovy.personalwebsiteblogapi.post;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import pl.borovy.personalwebsiteblogapi.model.Post;

public interface PostRepository extends CrudRepository<Post, Long> {

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
