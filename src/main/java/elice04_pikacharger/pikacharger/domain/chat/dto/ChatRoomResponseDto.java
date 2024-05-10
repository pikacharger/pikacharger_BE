package elice04_pikacharger.pikacharger.domain.chat.dto;

import elice04_pikacharger.pikacharger.domain.chat.entity.ChatLog;
import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;
import elice04_pikacharger.pikacharger.domain.user.entity.User;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class ChatRoomResponseDto {
    private Long chatRoomId;
    private Long chargerId;
    private String userImgUrl;
    private String nickname;
    private LocalDateTime createDate;
    private String lastMessage;

    public static ChatRoomResponseDto toEntity(Long chatRoomId, User user, ChatRoom chatRoom, ChatLog lastChatLog) {
        return ChatRoomResponseDto.builder()
                .chatRoomId(chatRoomId)
                .chargerId(chatRoom.getCharger().getId())
                .userImgUrl(user.getProfileImage())
                .nickname(user.getNickName())
                .createDate(chatRoom.getCreateDate())
                .lastMessage(lastChatLog != null ? lastChatLog.getMessageContents() : "대화를 기다리고 있어요. 무엇이든 물어보세요!")
                .build();
    }

    @Builder
    public ChatRoomResponseDto(Long chatRoomId, Long chargerId, String userImgUrl, String nickname, LocalDateTime createDate, String lastMessage) {
        this.chatRoomId = chatRoomId;
        this.chargerId = chargerId;
        this.userImgUrl = userImgUrl;
        this.nickname = nickname;
        this.createDate = createDate;
        this.lastMessage = lastMessage;
    }
}