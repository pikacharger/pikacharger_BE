package elice04_pikacharger.pikacharger.domain.chat.dto;

import elice04_pikacharger.pikacharger.domain.chat.entity.ChatLog;
import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatLogRequestDto {

    private String messageContents;

    public ChatLog toEntity(ChatRoom chatRoom, User sender) {
        return ChatLog.builder()
                .chatRoom(chatRoom)
                .messageContents(this.messageContents)
                .sender(sender)
                .build();
    }
}