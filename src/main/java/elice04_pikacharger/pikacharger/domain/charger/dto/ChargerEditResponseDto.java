package elice04_pikacharger.pikacharger.domain.charger.dto;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.chargertype.dto.payload.ChargerTypeDto;
import elice04_pikacharger.pikacharger.domain.chargertype.entity.ChargerType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChargerEditResponseDto {

    private String chargerLocation;
    private String chargerName;
    private String chargingSpeed;
    private String content;
    private double personalPrice;
    private List<ChargerType> chargerTypeList;
//    private List<ChargerImage> chargerImageList;


    public static ChargerEditResponseDto toDto(Charger charger) {
        return ChargerEditResponseDto.builder()
                .chargerLocation(charger.getChargerLocation())
                .chargerName(charger.getChargerName())
                .chargingSpeed(charger.getChargingSpeed())
                .content(charger.getContent())
                .personalPrice(charger.getPersonalPrice())
                .chargerTypeList(charger.getChargerTypes())
                .build();
    }

    @Builder
    public ChargerEditResponseDto(String chargerLocation, String chargerName, String chargingSpeed, String content, double personalPrice, List<ChargerType> chargerTypeList) {
        this.chargerLocation = chargerLocation;
        this.chargerName = chargerName;
        this.chargingSpeed = chargingSpeed;
        this.content = content;
        this.personalPrice = personalPrice;
        this.chargerTypeList = chargerTypeList;
    }
}
