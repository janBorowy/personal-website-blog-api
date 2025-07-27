package pl.borovy.personalwebsiteblogapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Date;
import java.time.LocalDate;
import pl.borovy.personalwebsiteblogapi.model.Post;
import pl.borovy.personalwebsiteblogapi.model.User;

public class StaticTestObjects {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final User USER = User.builder()
            .username("user1")
            .encodedPassword("123")
            .email("user@mail.com")
            .createdAt(Date.valueOf(LocalDate.now()))
            .build();

    public static final User ADMIN = User.builder()
            .username("admin")
            .encodedPassword("admin")
            .email("admin@mail.com")
            .createdAt(Date.valueOf(LocalDate.now()))
            .build();

    public static final Post POST = Post.builder()
            .author(ADMIN)
            .title("post title")
            .content("post content")
            .createdAt(Date.valueOf(LocalDate.now()))
            .build();
}
