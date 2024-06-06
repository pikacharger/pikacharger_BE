package elice04_pikacharger.pikacharger.domain.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import elice04_pikacharger.pikacharger.domain.comment.domain.Comment;
import elice04_pikacharger.pikacharger.domain.comment.dto.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

import static elice04_pikacharger.pikacharger.domain.comment.dto.CommentResponseDto.convertCommentToDto;
import static elice04_pikacharger.pikacharger.domain.comment.domain.QComment.comment;


@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommentResponseDto> findByReviewId(Long id) {
        List<Comment> comments = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .where(comment.review.id.eq(id))
                .orderBy(comment.parent.id.asc().nullsFirst(),
                        comment.createDate.asc())
                .fetch();

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        Map<Long, CommentResponseDto> commentDTOHashMap = new HashMap<>();

        comments.forEach(c ->{
            CommentResponseDto commentResponseDto = convertCommentToDto(c);
            commentDTOHashMap.put(commentResponseDto.getId(), commentResponseDto);
            // 최상위 댓글
            if (c.getParent() != null) commentDTOHashMap.get(c.getParent().getId()).getChildren().add(commentResponseDto);
            else commentResponseDtoList.add(commentResponseDto);
        });
        return commentResponseDtoList;
    }

    @Override
    public Optional<Comment> findCommentByIdWithParent(Long id){
        Comment selectedComment = queryFactory.select(comment)
                .from(comment)
                .leftJoin(comment.parent).fetchJoin()
                .where(comment.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(selectedComment);
    }
}
