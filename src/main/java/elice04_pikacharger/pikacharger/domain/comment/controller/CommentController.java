package elice04_pikacharger.pikacharger.domain.comment.controller;

import elice04_pikacharger.pikacharger.domain.comment.domain.Comment;
import elice04_pikacharger.pikacharger.domain.comment.dto.CommentResponseDto;
import elice04_pikacharger.pikacharger.domain.comment.dto.CommentResultDto;
import elice04_pikacharger.pikacharger.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "댓글 등록", description = "댓글을 등록합니다.")
    @PostMapping("/{reviewId}")
    public ResponseEntity<Comment> createComment(@AuthenticationPrincipal Long userId, @PathVariable Long reviewId, @RequestBody CommentResultDto commentResultDto){
        Comment comment = commentService.saveComment(userId, reviewId, commentResultDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    @PutMapping("/update/{commentId}")
    public ResponseEntity<String> updateComment(@AuthenticationPrincipal Long userId, @PathVariable Long commentId, @RequestBody CommentResultDto commentResultDto){
        return commentService.update(userId, commentId, commentResultDto);
    }


    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @DeleteMapping("/delete/{commentId}")
    //TODO return Type 명시하기
    public void delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
//        return ;
    }

    // 특정 리뷰 ID의 댓글 목록을 가져오는 엔드포인트
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByReviewId(@PathVariable Long reviewId) {
        List<CommentResponseDto> comments = commentService.findCommentsByReviewId(reviewId);
        return ResponseEntity.ok(comments);
    }

    // 특정 댓글 ID로 댓글을 조회하는 엔드포인트
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long commentId) {
        CommentResponseDto comment = commentService.findCommentById(commentId);
        return ResponseEntity.ok(comment);
    }

}
