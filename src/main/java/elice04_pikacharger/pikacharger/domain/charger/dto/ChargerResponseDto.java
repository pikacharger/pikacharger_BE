package elice04_pikacharger.pikacharger.domain.charger.dto;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ChargerResponseDto {

    private String chargerLocation;
    private String chargerName;
    private String chargingSpeed;
    private double latitude;
    private double longitude;
    private String content;
    private double personalPrice;

    public static ChargerResponseDto toDto(Charger charger){
        return ChargerResponseDto.builder()
                .chargerLocation(charger.getChargerLocation())
                .chargerName(charger.getChargerName())
                .chargingSpeed(charger.getChargingSpeed())
                .latitude(charger.getLatitude())
                .longitude(charger.getLongitude())
                .content(charger.getContent())
                .personalPrice(charger.getPersonalPrice())
                .build();
    }

    @Builder
    private ChargerResponseDto(String chargerLocation, String chargerName, String chargingSpeed, double latitude, double longitude, String content, double personalPrice){
        this.chargerLocation = chargerLocation;
        this.chargerName = chargerName;
        this.chargingSpeed = chargingSpeed;
        this.latitude = latitude;
        this.longitude = longitude;
        this.content = content;
        this.personalPrice = personalPrice;
    }


}
