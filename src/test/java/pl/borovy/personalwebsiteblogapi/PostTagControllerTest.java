package pl.borovy.personalwebsiteblogapi;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import pl.borovy.personalwebsiteblogapi.model.PostTagReference;
import pl.borovy.personalwebsiteblogapi.model.requests.AttachATagRequest;
import pl.borovy.personalwebsiteblogapi.model.requests.CreatePostTagRequest;
import pl.borovy.personalwebsiteblogapi.model.requests.DeattachATagRequest;
import pl.borovy.personalwebsiteblogapi.post.PostTagReferenceRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@Import(PostgresTestContainerConfig.class)
class PostTagControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PostTagReferenceRepository postTagReferenceRepository;

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
        mvc.perform(post(getFullUrl("/post-tag"))
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
        mvc.perform(post(getFullUrl("/post-tag"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(CreatePostTagRequest.builder()
                                .name(POST_TAG.getName())
                                .description(POST_TAG.getDescription())
                                .build())))
                .andExpect(status().isForbidden());

    }

    @WithAdmin
    @Test
    void createAPostTag() throws Exception {
        mvc.perform(post(getFullUrl("/post-tag"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(CreatePostTagRequest.builder()
                                .name(POST_TAG.getName())
                                .description(POST_TAG.getDescription())
                                .build())))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(POST_TAG.getName()))
                .andExpect(jsonPath("$.description").value(POST_TAG.getDescription()));
    }

    @WithAnonymousUser
    @Test
    void forbidAnonymousFromAttachingATag() throws Exception {
        mvc.perform(post(getFullUrl("/post-tag/attach"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(AttachATagRequest.builder()
                                .tagId(1)
                                .postId(1)
                                .build())))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser
    @Test
    void forbidUserFromAttachingATag() throws Exception {
        mvc.perform(post(getFullUrl("/post-tag/attach"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(AttachATagRequest.builder()
                                .tagId(1)
                                .postId(1)
                                .build())))
                .andExpect(status().isForbidden());
    }

    @WithAdmin
    @Test
    void attachATag() throws Exception {
        mvc.perform(post(getFullUrl("/post-tag/attach"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(AttachATagRequest.builder()
                                .tagId(1)
                                .postId(1)
                                .build())))
                .andExpect(status().isOk());

        assertTrue(postTagReferenceRepository.existsByPostIdAndTagId(1, 1));
    }

    @WithAnonymousUser
    @Test
    void forbidAnonymousFromDeattachingATag() throws Exception {
        mvc.perform(post(getFullUrl("/post-tag/deattach"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(DeattachATagRequest.builder()
                                .tagId(1)
                                .postId(1)
                                .build())))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser
    @Test
    void forbidUserFromDeattachingATag() throws Exception {
        mvc.perform(post(getFullUrl("/post-tag/deattach"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(DeattachATagRequest.builder()
                                .tagId(1)
                                .postId(1)
                                .build())))
                .andExpect(status().isForbidden());
    }

    @WithAdmin
    @Test
    void deattachATag() throws Exception {
        postTagReferenceRepository.save(new PostTagReference(1L, 1L));

        mvc.perform(post(getFullUrl("/post-tag/deattach"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(DeattachATagRequest.builder()
                                .tagId(1)
                                .postId(1)
                                .build())))
                .andExpect(status().isOk());

        assertFalse(postTagReferenceRepository.existsByPostIdAndTagId(1, 1));
    }

    @WithAnonymousUser
    @Test
    void forbidDeleteForAnonymousUser() throws Exception {
        mvc.perform(delete("/post-tag/1"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser
    @Test
    void forbidDeleteForUser() throws Exception {
        mvc.perform(delete("/post-tag/1"))
                .andExpect(status().isForbidden());
    }

    @WithAdmin
    @Test
    void deleteATag() throws Exception {
        postTagReferenceRepository.save(new PostTagReference(1L, 1L));
        postTagReferenceRepository.save(new PostTagReference(1L, 2L));
        postTagReferenceRepository.save(new PostTagReference(2L, 1L));

        mvc.perform(delete("/post-tag/1"))
                .andExpect(status().isNoContent());

        assertFalse(postTagReferenceRepository.existsByPostIdAndTagId(1, 1));
        assertFalse(postTagReferenceRepository.existsByPostIdAndTagId(2, 1));
        assertTrue(postTagReferenceRepository.existsByPostIdAndTagId(1, 2));
    }

}
