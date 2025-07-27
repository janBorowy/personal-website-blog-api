package pl.borovy.personalwebsiteblogapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.borovy.personalwebsiteblogapi.model.PostTag;
import pl.borovy.personalwebsiteblogapi.model.response.PostTagResponse;

@Mapper
public interface PostTagToPostTagResponseMapper {

   PostTagToPostTagResponseMapper INSTANCE = Mappers.getMapper(PostTagToPostTagResponseMapper.class);

   PostTagResponse map(PostTag postTag);

}
