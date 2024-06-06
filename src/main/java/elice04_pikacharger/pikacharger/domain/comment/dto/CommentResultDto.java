package elice04_pikacharger.pikacharger.domain.comment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResultDto {
    private Long userId;
    private Long parentId;
    private String content;

    public CommentResultDto(String content){
        this.content = content;
    }

}
