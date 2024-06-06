package elice04_pikacharger.pikacharger.domain.comment.mapper;

import elice04_pikacharger.pikacharger.domain.comment.domain.Comment;
import elice04_pikacharger.pikacharger.domain.comment.dto.CommentResultDto;
import elice04_pikacharger.pikacharger.domain.common.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentRequestMapper extends EntityMapper<CommentResultDto, Comment> {
    CommentRequestMapper INSTANCE = Mappers.getMapper(CommentRequestMapper.class);
}
