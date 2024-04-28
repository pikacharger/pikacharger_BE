package elice04_pikacharger.pikacharger.domain.chatlog;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatLogDto {
    public enum MessageType{
        ENTER, TALK
    }

    private MessageType messageType;
    private Long chatRoomId;
    private Long senderId;
    //userSto에서 사용자 id, nickname, profile_image를 받아와야해서 인혁님이랑 얘기해야할 거 같음
    private String message;
}
