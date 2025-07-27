package pl.borovy.personalwebsiteblogapi.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.borovy.personalwebsiteblogapi.model.Post;
import pl.borovy.personalwebsiteblogapi.model.response.PostResponse;

@Mapper(uses = {
        UserToUserResponseMapper.class
})
public interface PostToPostResponseMapper {

    PostToPostResponseMapper INSTANCE = Mappers.getMapper(PostToPostResponseMapper.class);

    PostResponse map(Post post);

}
