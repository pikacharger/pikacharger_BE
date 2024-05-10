package elice04_pikacharger.pikacharger.domain.chat.dto;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;

import elice04_pikacharger.pikacharger.domain.image.domain.ReviewImage;
import elice04_pikacharger.pikacharger.domain.review.domain.Review;
import elice04_pikacharger.pikacharger.domain.review.dto.result.ReviewResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomRequestDto { //list 반환
    private Long chatRoomId;
    private Long chargerId;
    private Long senderId;
    private Long userId;
    private String chargerName;
    private String nickName;
    private String imgUrl;
    private LocalDateTime createAt;

    public static ChatRoomRequestDto toDto(ChatRoom chatRoom){
        ChatRoomRequestDto result = ChatRoomRequestDto.builder()
                .chatRoomId(chatRoom.getId())
                .chargerId(chatRoom.getCharger().getId())
                .userId(chatRoom.getUser().getId())
                .senderId(chatRoom.getCharger().getUser().getId())
                .imgUrl(chatRoom.getUser().getProfileImage())
                .chargerName(chatRoom.getCharger().getChargerName())
                .nickName(chatRoom.getCharger().getUser().getNickName())
                .createAt(chatRoom.getCreateDate())
                .build();
        return result;
    }

    @Builder
    protected ChatRoomRequestDto(Long chatRoomId, Long chargerId, Long senderId, String chargerName, LocalDateTime createAt, String nickName, String imgUrl, Long userId){
        this.chargerId = chargerId;
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.chargerName = chargerName;
        this.nickName = nickName;
        this.imgUrl = imgUrl;
        this.createAt = createAt;
        this.userId = userId;
    }
}