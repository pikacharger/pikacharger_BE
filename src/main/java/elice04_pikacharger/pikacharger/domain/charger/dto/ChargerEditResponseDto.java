package elice04_pikacharger.pikacharger.domain.charger.dto;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.chargertype.entity.ChargerType;
import elice04_pikacharger.pikacharger.domain.image.domain.ChargerImage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChargerEditResponseDto {

    private Long chargerId;
    private String chargerLocation;
    private String chargerName;
    private String chargingSpeed;
    private String content;
    private double personalPrice;
    private List<ChargerType> chargerTypeList;
    private List<ChargerImage> chargerImageList;

    public static ChargerEditResponseDto toDto(Charger charger) {
        return ChargerEditResponseDto.builder()
                .chargerId(charger.getId())
                .chargerLocation(charger.getChargerLocation())
                .chargerName(charger.getChargerName())
                .chargingSpeed(charger.getChargingSpeed())
                .content(charger.getContent())
                .personalPrice(charger.getPersonalPrice())
                .chargerTypeList(charger.getChargerTypes())
                .chargerImageList(charger.getChargerImages())
                .build();
    }

    @Builder
    public ChargerEditResponseDto(Long chargerId, String chargerLocation, String chargerName, String chargingSpeed, String content, double personalPrice, List<ChargerType> chargerTypeList, List<ChargerImage> chargerImageList) {
        this.chargerId = chargerId;
        this.chargerLocation = chargerLocation;
        this.chargerName = chargerName;
        this.chargingSpeed = chargingSpeed;
        this.content = content;
        this.personalPrice = personalPrice;
        this.chargerTypeList = chargerTypeList;
        this.chargerImageList = chargerImageList;
    }
}
