package elice04_pikacharger.pikacharger.domain.charger.dto;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.chargertype.entity.ChargerType;
import elice04_pikacharger.pikacharger.domain.favorite.dto.payload.FavoriteResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ChargerSearchResponseDto {

    private String chargerLocation;
    private String chargerName;
    private double latitude;
    private double longitude;
    private List<ChargerType> chargerTypeList;
    private String chargerRole;
    private double avgRate;
    private String chargerStatus;
    private String chargingSpeed;

    public static ChargerSearchResponseDto toDto(Charger charger){
        return ChargerSearchResponseDto.builder()
                .chargerLocation(charger.getChargerLocation())
                .chargerName(charger.getChargerName())
                .latitude(charger.getLatitude())
                .longitude(charger.getLongitude())
                .chargerTypeList(charger.getChargerTypes())
                .chargerRole(charger.getChargerRole().getMessage())
                .avgRate(charger.getAvgRate())
                .chargerStatus(charger.getChargerStatus())
                .chargingSpeed(charger.getChargingSpeed())
                .build();
    }

    @Builder
    private ChargerSearchResponseDto(String chargerLocation, String chargerName, double latitude, double longitude, List<ChargerType> chargerTypeList, String chargerRole, double avgRate, String chargerStatus, String chargingSpeed){
        this.chargerLocation = chargerLocation;
        this.chargerName = chargerName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.chargerTypeList = chargerTypeList;
        this.chargerRole = chargerRole;
        this.avgRate = avgRate;
        this.chargerStatus = chargerStatus;
        this.chargingSpeed = chargingSpeed;
    }
}
