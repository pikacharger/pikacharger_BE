package elice04_pikacharger.pikacharger.domain.chatlog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
@Getter
@Builder
@AllArgsConstructor
public class ChatLogResponseDto {
    private Long chatLogId;
    private Long chatRoomId;
    private Long userId;
    private String messageContents;
    private Date created;
}
