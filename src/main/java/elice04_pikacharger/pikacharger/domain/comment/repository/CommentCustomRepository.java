package elice04_pikacharger.pikacharger.domain.comment.repository;

import elice04_pikacharger.pikacharger.domain.comment.domain.Comment;
import elice04_pikacharger.pikacharger.domain.comment.dto.CommentResponseDto;

import java.util.List;
import java.util.Optional;

public interface CommentCustomRepository {
    List<CommentResponseDto> findByReviewId(Long id);

    Optional<Comment> findCommentByIdWithParent(Long id);
}
