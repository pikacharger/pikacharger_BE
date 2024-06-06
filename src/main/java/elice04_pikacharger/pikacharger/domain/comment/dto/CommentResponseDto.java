package elice04_pikacharger.pikacharger.domain.comment.dto;

import elice04_pikacharger.pikacharger.domain.comment.domain.Comment;
import elice04_pikacharger.pikacharger.domain.user.dto.payload.UserGetDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponseDto {
    private Long id;
    private String content;
    private UserGetDto user;
    private List<CommentResponseDto> children = new ArrayList<>();

    public CommentResponseDto(Long id, String content, UserGetDto user){
        this.id = id;
        this.content = content;
        this.user = user;
    }

    // comment 객체를 commentResponseDto로 변환
    public static CommentResponseDto convertCommentToDto(Comment comment){
        return comment.getIsDeleted() ?
                new CommentResponseDto(comment.getId(), "삭제된 댓글입니다.", null) :
                // 댓글이 삭제되지 않은 경우 댓글 내용을 포함한 dto 생성. (user정보는 UserGetDto로 감싸서 전달)
                new CommentResponseDto(comment.getId(), comment.getContent(), new UserGetDto(comment.getUser()));
    }
}
