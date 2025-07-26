package pl.borovy.personalwebsiteblogapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Date;
import java.time.LocalDate;
import pl.borovy.personalwebsiteblogapi.model.User;

public class StaticTestObjects {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final User USER = User.builder()
            .username("user")
            .encodedPassword("123")
            .email("user@mail.com")
            .createdAt(Date.valueOf(LocalDate.now()))
            .build();

}
