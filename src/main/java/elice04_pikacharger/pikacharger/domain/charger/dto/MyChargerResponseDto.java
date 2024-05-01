package elice04_pikacharger.pikacharger.domain.charger.dto;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.chargertype.entity.ChargerType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MyChargerResponseDto {

    private Long chargerId;
    private String chargerLocation;
    private String chargerName;
    private double latitude;
    private double longitude;
    private List<ChargerType> chargerTypeList;
    private String chargerRole;
    private double avgRate;
    private String chargerStatus;

    public static MyChargerResponseDto toDto(Charger charger){
        return MyChargerResponseDto.builder()
                .chargerId(charger.getId())
                .chargerLocation(charger.getChargerLocation())
                .chargerName(charger.getChargerName())
                .latitude(charger.getLatitude())
                .longitude(charger.getLongitude())
                .chargerTypeList(charger.getChargerTypes())
                .chargerRole(charger.getChargerRole().getMessage())
                .avgRate(charger.getAvgRate())
                .chargerStatus(charger.getChargerStatus())
                .build();
    }

    @Builder
    private MyChargerResponseDto(Long chargerId, String chargerLocation, String chargerName, double latitude, double longitude, List<ChargerType> chargerTypeList, String chargerRole, double avgRate, String chargerStatus){
        this.chargerId = chargerId;
        this.chargerLocation = chargerLocation;
        this.chargerName = chargerName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.chargerTypeList = chargerTypeList;
        this.chargerRole = chargerRole;
        this.avgRate = avgRate;
        this.chargerStatus = chargerStatus;
    }
}
