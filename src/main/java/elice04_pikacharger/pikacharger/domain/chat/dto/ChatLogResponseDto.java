package elice04_pikacharger.pikacharger.domain.chat.dto;

import elice04_pikacharger.pikacharger.domain.chat.entity.ChatLog;
import elice04_pikacharger.pikacharger.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
@Getter
@Builder
@AllArgsConstructor
public class ChatLogResponseDto{
    private Long id;
    private User sender;
    private String messageContents;
    private String createdDate;
    private String lastModifiedDate;

    public ChatLogResponseDto(ChatLog entity) {
        this.id = entity.getId();
        this.sender = entity.getSender();
        this.messageContents = entity.getMessageContents();
        this.createdDate = entity.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        this.lastModifiedDate = entity.getLastModifiedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }
}
