package elice04_pikacharger.pikacharger.domain.comment.service;

import com.amazonaws.services.kms.model.NotFoundException;
import elice04_pikacharger.pikacharger.domain.comment.domain.Comment;
import elice04_pikacharger.pikacharger.domain.comment.dto.CommentResponseDto;
import elice04_pikacharger.pikacharger.domain.comment.dto.CommentResultDto;
import elice04_pikacharger.pikacharger.domain.comment.mapper.CommentRequestMapper;
import elice04_pikacharger.pikacharger.domain.comment.repository.CommentRepository;
import elice04_pikacharger.pikacharger.domain.common.exceptional.CommentErrorCode;
import elice04_pikacharger.pikacharger.domain.common.exceptional.CustomException;
import elice04_pikacharger.pikacharger.domain.common.exceptional.UserErrorCode;
import elice04_pikacharger.pikacharger.domain.review.domain.Review;
import elice04_pikacharger.pikacharger.domain.review.repository.ReviewRepository;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import elice04_pikacharger.pikacharger.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final CommentRequestMapper commentRequestMapper;

    @Transactional
    public Comment saveComment(Long userId, Long reviewId, CommentResultDto commentResultDto){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("Could not found review id : " + reviewId));

        Comment parentComment = null;
        if (commentResultDto.getParentId() != null && commentResultDto.getParentId() != 0) {
            parentComment = commentRepository.findById(commentResultDto.getParentId())
                    .orElseThrow(() -> new NotFoundException("Could not found comment id : " + commentResultDto.getParentId()));
        }

        // Comment 객체 생성
        Comment comment = commentRepository.save(
                Comment.builder()
                    .content(commentResultDto.getContent())
                    .user(user)
                    .review(review)
                    .parent(parentComment)
                    .build());

        // 부모 댓글이 있다면 자식 댓글로 추가
        if (parentComment != null) {
            parentComment.getChildren().add(comment);
            commentRepository.save(parentComment);
        }
        return comment;
    }

    @Transactional
    public ResponseEntity<String> update(Long userId, Long commentId, CommentResultDto commentResultDto){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(CommentErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getUser().getId().equals(userId)) {
            throw new CustomException(UserErrorCode.USER_MISMATCH);
        }

        comment.updateContent(commentResultDto.getContent());
        return ResponseEntity.ok("Comment updated successfully");
    }

    @Transactional
    public void delete(Long commentId){
        Comment comment = commentRepository.findCommentByIdWithParent(commentId)
                .orElseThrow(() -> new CustomException(CommentErrorCode.COMMENT_NOT_FOUND));

        if(comment.getChildren().size() != 0){ // 자식이 있다면
            comment.changeIsDeleted(true);
        } else { //삭제 가능한 부모 댓글 삭제
            commentRepository.delete(getDeletableAncestorComment(comment));
        }
    }

    private Comment getDeletableAncestorComment(Comment comment){
        Comment parent = comment.getParent(); // 현재 댓글의 부모 댓글 구하기.

        // 부모가 있고, 부모의 자식이 1개이고(현재 삭제하는 댓글), 부모의 삭제 상태가 true 인 댓글을 재귀
        if(parent != null && parent.getChildren().size() ==1 && parent.getIsDeleted())
            return getDeletableAncestorComment(parent);

        // 삭제해야하는 댓글 반환
        return comment;
    }

    public List<CommentResponseDto> findByReviewId(Long id) {
        return commentRepository.findByReviewId(id);
    }

    public Optional<Comment> findCommentById(Long id) {
        return commentRepository.findCommentByIdWithParent(id);
    }

    public List<CommentResponseDto> findByParentId(Long parentId) {
        Optional<Comment> comments = commentRepository.findCommentByIdWithParent(parentId);
        return comments.stream()
                .map(CommentResponseDto::convertCommentToDto)
                .collect(Collectors.toList());
    }
}
