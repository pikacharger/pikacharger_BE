package elice04_pikacharger.pikacharger.domain.chatlog;

import lombok.Getter;

@Getter
public class ChatLogRequestDto {
    public enum MessageType{
        ENTER, TALK
    }
    private MessageType type;
    private Long userId;
    private String message;
}