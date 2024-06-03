package elice04_pikacharger.pikacharger.domain.chat.dto;

import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
public class SingleChatRoomResponseDto {
    private Long chargerId;
    private String chargerName;
    private String chargerImg;
    private String chargingSpeed;
    private LocalDateTime createDate;
    private List<UserInfo> user;

    @Data
    @Builder
    public static class UserInfo {
        private Long id;
    }

    public static SingleChatRoomResponseDto toEntity(ChatRoom chatRoom) {
        // TODO: 첫번째 충전기 사진만 보내고 없을 시 빈문자열 반환
        String firstChargerImg = "";
//        if (chatLog.getChatRoom().getCharger().getChargerImages() != null && !chatLog.getChatRoom().getCharger().getChargerImages().isEmpty()) {
//            firstChargerImg = chatLog.getChatRoom().getCharger().getChargerImages().get(0);
//        }

        List<UserInfo> user = Arrays.asList(
                UserInfo.builder().id(chatRoom.getUser().getId()).build(),
                UserInfo.builder().id(chatRoom.getReceiverId().getId()).build()
        );

        return SingleChatRoomResponseDto.builder()
                .chargerId(chatRoom.getCharger().getId())
                .chargerName(chatRoom.getCharger().getChargerName())
                .chargerImg(firstChargerImg)
                .chargingSpeed(chatRoom.getCharger().getChargingSpeed())
                .createDate(chatRoom.getCreateDate())
                .user(user)
                .build();
    }

    @Builder
    public SingleChatRoomResponseDto(Long chargerId, String chargerName, String chargerImg, String chargingSpeed, LocalDateTime createDate, List<UserInfo> user) {
        this.chargerId = chargerId;
        this.chargerName = chargerName;
        this.chargerImg = chargerImg;
        this.chargingSpeed = chargingSpeed;
        this.createDate = createDate;
        this.user = user;
    }
}
