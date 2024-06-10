package elice04_pikacharger.pikacharger.domain.comment.mapper;

import elice04_pikacharger.pikacharger.domain.comment.domain.Comment;
import elice04_pikacharger.pikacharger.domain.comment.dto.CommentResultDto;
import elice04_pikacharger.pikacharger.domain.common.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentRequestMapper extends EntityMapper<CommentResultDto, Comment> {
    CommentRequestMapper INSTANCE = Mappers.getMapper(CommentRequestMapper.class);

//    @Mapping(target = "content", source = "content")
//    @Mapping(target = "parent.id", source = "parentId") // 매핑 추가
//    Comment toEntity(CommentResultDto dto);
}
