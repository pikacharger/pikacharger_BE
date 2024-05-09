package elice04_pikacharger.pikacharger.domain.chat.dto;

import elice04_pikacharger.pikacharger.domain.chat.entity.ChatLog;
import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;

import elice04_pikacharger.pikacharger.domain.user.entity.User;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
public class ChatRoomResponseDto {

    private Long chargerId;
    private String userImgUrl;
    private String nickname;
    private LocalDateTime createDate;

    public static ChatRoomResponseDto toEntity(User user, ChatRoom chatRoom) {
        return ChatRoomResponseDto.builder()
                .chargerId(chatRoom.getCharger().getId())
                .userImgUrl(user.getProfileImage())
                .nickname(user.getNickName())
                .createDate(chatRoom.getCreateDate())
                .build();
    }

    @Builder
    public ChatRoomResponseDto(Long chargerId, String userImgUrl, String nickname, LocalDateTime createDate) {
        this.chargerId = chargerId;
        this.userImgUrl = userImgUrl;
        this.nickname = nickname;
        this.createDate = createDate;
    }
}