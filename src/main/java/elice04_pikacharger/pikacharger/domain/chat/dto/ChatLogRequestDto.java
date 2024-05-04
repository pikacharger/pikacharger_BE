package elice04_pikacharger.pikacharger.domain.chat.dto;

import elice04_pikacharger.pikacharger.domain.chat.entity.ChatLog;
import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatLogRequestDto {
    private User sender;
    private String messageContents;
    private ChatRoom chatRoom;

    @Builder
    public ChatLogRequestDto(User sender, String messageContents, ChatRoom chatRoom) {
        this.sender = sender;
        this.messageContents = messageContents;
        this.chatRoom = chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public ChatLog toEntity() {
        return ChatLog.builder()
                .sender(this.sender)
                .messageContents(this.messageContents)
                .chatRoom(this.chatRoom)
                .build();
    }
}