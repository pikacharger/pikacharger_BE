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
    private String messageContents;
    private Long chargerId;
    private String chargerName;
    private String chargerImg;
    private String chargerLocation;
    private Long senderId;
    private String nickName;
    private String profileImg;
    private Long userId;
    private Long chatRoomId;
    private LocalDateTime createDate;

    public static ChatLogResponseDto toEntity(ChatLog chatLog) {
        String firstChargerImg = "";
//        if (chatLog.getChatRoom().getCharger().getChargerImages() != null && !chatLog.getChatRoom().getCharger().getChargerImages().isEmpty()) {
//            firstChargerImg = chatLog.getChatRoom().getCharger().getChargerImages().get(0);
//        }

        return ChatLogResponseDto.builder()
                .chatLogId(chatLog.getId())
                .messageContents(chatLog.getMessageContents())
                .chargerId(chatLog.getChatRoom().getCharger().getId())
                .chargerName(chatLog.getChatRoom().getCharger().getChargerName())
                .chargerImg(firstChargerImg)
                .chargerLocation(chatLog.getChatRoom().getCharger().getChargerLocation())
                .senderId(chatLog.getSender().getId())
                .nickName(chatLog.getSender().getNickName())
                .profileImg(chatLog.getSender().getProfileImage())
                .userId(chatLog.getChatRoom().getUser().getId())
                .chatRoomId(chatLog.getChatRoom().getId())
                .createDate(chatLog.getCreateDate())
                .build();
    }

    @Builder
    public ChatLogResponseDto(Long chatLogId, String messageContents, Long chargerId, String chargerName, String chargerImg, String chargerLocation, Long senderId, String nickName, String profileImg, Long userId, Long chatRoomId, LocalDateTime createDate) {
        this.chatLogId = chatLogId;
        this.messageContents = messageContents;
        this.chargerId = chargerId;
        this.chargerName = chargerName;
        this.chargerImg = chargerImg;
        this.chargerLocation = chargerLocation;
        this.senderId = senderId;
        this.nickName = nickName;
        this.profileImg = profileImg;
        this.userId = userId;
        this.chatRoomId = chatRoomId;
        this.createDate = createDate;
    }
}