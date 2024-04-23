package elice04_pikacharger.pikacharger.domain.charger.dto;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ChargerRequestDto {

    private String chargerLocation;
    private String chargerName;
    private String chargingSpeed;
    private double latitude;
    private double longitude;
    private String content;
    private int personalPrice;

    public static ChargerRequestDto toDto(Charger charger){
        return ChargerRequestDto.builder()
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
    private ChargerRequestDto(String chargerLocation, String chargerName, String chargingSpeed, double latitude, double longitude, String content, int personalPrice){
        this.chargerLocation = chargerLocation;
        this.chargerName = chargerName;
        this.chargingSpeed = chargingSpeed;
        this.latitude = latitude;
        this.longitude = longitude;
        this.content = content;
        this.personalPrice = personalPrice;
    }


}
