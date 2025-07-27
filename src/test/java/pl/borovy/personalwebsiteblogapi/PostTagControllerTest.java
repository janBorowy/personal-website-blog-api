package pl.borovy.personalwebsiteblogapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.borovy.personalwebsiteblogapi.StaticTestObjects.OBJECT_MAPPER;
import static pl.borovy.personalwebsiteblogapi.StaticTestObjects.POST_TAG;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.borovy.personalwebsiteblogapi.model.requests.CreatePostTagRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@Import(PostgresTestContainerConfig.class)
class PostTagControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private MockMvc mvc;

    private String getFullUrl(String path) {
        return "http://localhost:%s%s".formatted(port, path);
    }

    @WithAnonymousUser
    @Test
    void getPostTag() throws Exception {
        mvc.perform(get(getFullUrl("/post-tag/1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @WithAnonymousUser
    @Test
    void getPostTagWhichDoesNotExist() throws Exception {
        mvc.perform(get(getFullUrl("/post-tag/100")))
                .andExpect(status().isNotFound());
    }

    @WithAnonymousUser
    @Test
    void forbidAnonymousUserFromSavingAPostTag() throws Exception {
        mvc.perform(post(getFullUrl("/post"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(CreatePostTagRequest.builder()
                                .name(POST_TAG.getName())
                                .description(POST_TAG.getDescription())
                        .build())))
                .andExpect(status().isUnauthorized());

    }

    @WithMockUser
    @Test
    void forbidUserFromSavingAPostTag() throws Exception {
        mvc.perform(post(getFullUrl("/post"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(CreatePostTagRequest.builder()
                                .name(POST_TAG.getName())
                                .description(POST_TAG.getDescription())
                                .build())))
                .andExpect(status().isForbidden());

    }
}
