package elice04_pikacharger.pikacharger.domain.chat.dto;

import elice04_pikacharger.pikacharger.domain.chat.entity.ChatLog;
import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;
import elice04_pikacharger.pikacharger.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatLogResponseDto{
    private Long chatLogId;
    private User sender;
    private ChatRoom chatRoom;
    private String messageContents;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;

    public static ChatLogResponseDto toEntity(ChatLog chatLog) {
        return ChatLogResponseDto.builder()
                .chatLogId(chatLog.getId())
                .sender(chatLog.getSender())
                .chatRoom(chatLog.getChatRoom())
                .messageContents(chatLog.getMessageContents())
                .createDate(chatLog.getCreateDate())
                .lastModifiedDate(chatLog.getLastModifiedDate())
                .build();
    }

    @Builder
    public ChatLogResponseDto(Long chatLogId, User sender, ChatRoom chatRoom, String messageContents, LocalDateTime createDate, LocalDateTime lastModifiedDate) {
        this.chatLogId = chatLogId;
        this.sender = sender;
        this.chatRoom = chatRoom;
        this.messageContents = messageContents;
        this.createDate = createDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}