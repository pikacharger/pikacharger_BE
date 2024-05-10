package elice04_pikacharger.pikacharger.domain.chat.dto;

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

    public static ChatRoomResponseDto toEntity(Long chatRoomId, User user, ChatRoom chatRoom) {
        return ChatRoomResponseDto.builder()
                .chatRoomId(chatRoomId)
                .chargerId(chatRoom.getCharger().getId())
                .userImgUrl(user.getProfileImage())
                .nickname(user.getNickName())
                .createDate(chatRoom.getCreateDate())
                .build();
    }

    @Builder
    public ChatRoomResponseDto(Long chatRoomId, Long chargerId, String userImgUrl, String nickname, LocalDateTime createDate) {
        this.chatRoomId = chatRoomId;
        this.chargerId = chargerId;
        this.userImgUrl = userImgUrl;
        this.nickname = nickname;
        this.createDate = createDate;
    }
}