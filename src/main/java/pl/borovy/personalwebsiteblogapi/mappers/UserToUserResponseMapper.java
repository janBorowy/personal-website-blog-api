package pl.borovy.personalwebsiteblogapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.borovy.personalwebsiteblogapi.model.User;
import pl.borovy.personalwebsiteblogapi.user.UserResponse;

@Mapper
public interface UserToUserResponseMapper {

    UserToUserResponseMapper INSTANCE = Mappers.getMapper(UserToUserResponseMapper.class);

    UserResponse map(User user);

}
