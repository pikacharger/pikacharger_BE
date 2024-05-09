package elice04_pikacharger.pikacharger.domain.chat.dto;
import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;
import elice04_pikacharger.pikacharger.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ChatLogResponseDto{
    private Long id;
    private User sender;
    private ChatRoom chatRoom;
    private String messageContents;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;
}