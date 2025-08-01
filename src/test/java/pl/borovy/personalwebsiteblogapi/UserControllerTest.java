package pl.borovy.personalwebsiteblogapi;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.borovy.personalwebsiteblogapi.StaticTestObjects.OBJECT_MAPPER;

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
import pl.borovy.personalwebsiteblogapi.model.requests.UserRegisterRequest;
import pl.borovy.personalwebsiteblogapi.user.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@Import(PostgresTestContainerConfig.class)
class UserControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    private String getFullUrl(String path) {
        return "http://localhost:%s%s".formatted(port, path);
    }

    @WithAnonymousUser
    @Test
    void getUserByIdWhenNotAuthorized() throws Exception {
        mvc.perform(get(getFullUrl("/user/1")))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser
    @Test
    void getUserByIdWhenLoggedInAsUser() throws Exception {
        mvc.perform(get(getFullUrl("/user/1")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").exists())
                .andExpect(jsonPath("$.email").exists());
    }

    @WithMockUser
    @Test
    void getUserByIdWhichDoesNotExist() throws Exception {
        mvc.perform(get(getFullUrl("/user/100")))
                .andExpect(status().isNotFound());
    }

    @WithAnonymousUser
    @Test
    void registerNewUser() throws Exception {
        mvc.perform(post(getFullUrl("/user/register"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(
                                UserRegisterRequest.builder()
                                        .email("newUser@mail.com")
                                        .plainPassword("ja12345678")
                                        .username("new_user123")
                                        .build()
                        )))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("newUser@mail.com"))
                .andExpect(jsonPath("$.username").value("new_user123"))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.lastModifiedAt").exists());

        assertTrue(userRepository.findByEmail("newUser@mail.com").isPresent());
    }

}
