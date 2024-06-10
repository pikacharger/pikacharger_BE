package elice04_pikacharger.pikacharger.domain.comment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResultDto {
    private String content;
    private Long parentId;

    public CommentResultDto(String content, Long parentId){
        this.content = content;
        this.parentId = parentId;
    }
}
