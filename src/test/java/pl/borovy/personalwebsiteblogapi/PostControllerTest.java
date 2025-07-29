package pl.borovy.personalwebsiteblogapi;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.borovy.personalwebsiteblogapi.StaticTestObjects.ADMIN;
import static pl.borovy.personalwebsiteblogapi.StaticTestObjects.OBJECT_MAPPER;
import static pl.borovy.personalwebsiteblogapi.StaticTestObjects.POST;

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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@Import(PostgresTestContainerConfig.class)
class PostControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private MockMvc mvc;

    private String getFullUrl(String path) {
        return "http://localhost:%s%s".formatted(port, path);
    }

    @WithAnonymousUser
    @Test
    void restrictAnonymousUserFromPosting() throws Exception {
        mvc.perform(post(getFullUrl("/post"))
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(authorities = { "SCOPE_USER" })
    @Test
    void restrictUserFromPosting() throws Exception {
        mvc.perform(post(getFullUrl("/post"))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @WithAdmin
    @Test
    void postAsAdmin() throws Exception {
        mvc.perform(post(getFullUrl("/post"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(POST))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value(POST.getTitle()))
                .andExpect(jsonPath("$.content").value(POST.getContent()))
                .andExpect(jsonPath("$.createdBy.username").value(ADMIN.getUsername()))
                .andExpect(jsonPath("$.lastModifiedBy.username").value(ADMIN.getUsername()))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.lastModifiedAt").exists());
    }

    @Test
    void getPostAnonymous() throws Exception {
        mvc.perform(get(getFullUrl("/post/1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getPostWhichDoesNotExist() throws Exception {
        mvc.perform(get(getFullUrl("/post/100")))
                .andExpect(status().isNotFound());
    }

}
