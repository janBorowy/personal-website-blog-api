package pl.borovy.personalwebsiteblogapi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import pl.borovy.personalwebsiteblogapi.model.PostTag;
import pl.borovy.personalwebsiteblogapi.post.PostTagRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@Import(PostgresTestContainerConfig.class)
class PostTagRepositoryTest {

    @Autowired
    PostTagRepository postTagRepository;

    @Test
    void testFindByNameQuery() {
        postTagRepository.save(PostTag.builder()
                        .name("Computer science")
                .build());
        postTagRepository.save(PostTag.builder()
                .name("Graphics")
                .build());
        postTagRepository.save(PostTag.builder()
                .name("Graphics Computer")
                .build());
        postTagRepository.save(PostTag.builder()
                .name("Dumb computers")
                .build());

        var result = postTagRepository.findByName("computer", Pageable.unpaged());

        assertThat(result).hasSize(3)
                .extracting(PostTag::getName)
                .contains("Computer science", "Graphics Computer", "Dumb computers")
                .isSorted();
    }

}
