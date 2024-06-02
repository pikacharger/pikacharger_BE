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
    private Long chatRoomId;
    private Long chatLogId;
    private String messageContents;
    private LocalDateTime createDate;
    private Long userId;
    private String userProfileImg;
    private String userNickName;

    public static ChatLogResponseDto toEntity(ChatLog chatLog) {

        return ChatLogResponseDto.builder()
                .chatRoomId(chatLog.getChatRoom().getId())
                .chatLogId(chatLog.getId())
                .messageContents(chatLog.getMessageContents())
                .createDate(chatLog.getCreateDate())
                .userId(chatLog.getSender().getId())
                .userProfileImg(chatLog.getSender().getProfileImage())
                .userNickName(chatLog.getSender().getNickName())
                .build();
    }

    @Builder
    public ChatLogResponseDto(Long chatLogId, String messageContents, LocalDateTime createDate, Long userId, String userNickName, String userProfileImg, Long chatRoomId) {
        this.chatRoomId = chatRoomId;
        this.chatLogId = chatLogId;
        this.messageContents = messageContents;
        this.createDate = createDate;
        this.userId = userId;
        this.userProfileImg = userProfileImg;
        this.userNickName = userNickName;
    }
}