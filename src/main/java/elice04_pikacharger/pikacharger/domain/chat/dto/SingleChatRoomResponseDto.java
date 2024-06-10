package elice04_pikacharger.pikacharger.domain.chat.dto;

import elice04_pikacharger.pikacharger.domain.chargertype.entity.ChargerType;
import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;
import elice04_pikacharger.pikacharger.domain.image.domain.ChargerImage;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SingleChatRoomResponseDto {
    private Long chargerId;
    private String chargerName;
    private List<String> chargerImg;
    private String chargingType;
    private List<UserInfo> user;
    private LocalDateTime createDate;

    @Data
    @Builder
    public static class UserInfo {
        private Long id;
    }

    public static SingleChatRoomResponseDto toEntity(ChatRoom chatRoom) {

        List<String> chargerImg = chatRoom.getCharger().getChargerImages().stream()
                .map(ChargerImage::getImageUrl)
                .collect(Collectors.toList());

        String chargingType = chatRoom.getCharger().getChargerTypes().stream()
                .map(ChargerType::getType)
                .collect(Collectors.joining(","));

        List<UserInfo> user = Arrays.asList(
                UserInfo.builder().id(chatRoom.getUser().getId()).build(),
                UserInfo.builder().id(chatRoom.getReceiverId().getId()).build()
        );

        return SingleChatRoomResponseDto.builder()
                .chargerId(chatRoom.getCharger().getId())
                .chargerName(chatRoom.getCharger().getChargerName())
                .chargerImg(chargerImg)
                .chargingType(chargingType)
                .user(user)
                .createDate(chatRoom.getCreateDate())
                .build();
    }

    @Builder
    public SingleChatRoomResponseDto(Long chargerId, String chargerName, List<String> chargerImg, String chargingType, List<UserInfo> user, LocalDateTime createDate) {
        this.chargerId = chargerId;
        this.chargerName = chargerName;
        this.chargerImg = chargerImg;
        this.chargingType = chargingType;
        this.user = user;
        this.createDate = createDate;
    }
}