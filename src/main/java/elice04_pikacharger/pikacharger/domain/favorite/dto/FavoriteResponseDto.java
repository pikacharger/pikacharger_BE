package elice04_pikacharger.pikacharger.domain.favorite.dto;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.chargertype.entity.ChargerType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FavoriteResponseDto {

    private Long chargerId;
    private String chargerLocation;
    private String chargerName;
    private double latitude;
    private double longitude;
    private String chargingSpeed;
    private List<ChargerType> chargerTypeList;
    private String chargerRole;
    private boolean favorite;
    private double avgRate;
    private String chargerStatus;

    public static FavoriteResponseDto toDto(Charger charger, boolean favorite){
        return FavoriteResponseDto.builder()
                .chargerId(charger.getId())
                .chargerLocation(charger.getChargerLocation())
                .chargerName(charger.getChargerName())
                .latitude(charger.getLatitude())
                .longitude(charger.getLongitude())
                .chargingSpeed(charger.getChargingSpeed())
                .chargerTypeList(charger.getChargerTypes())
                .chargerRole(charger.getChargerRole().getMessage())
                .favorite(favorite)
                .avgRate(charger.getAvgRate())
                .chargerStatus(charger.getChargerStatus())
                .build();
    }

    @Builder
    private FavoriteResponseDto(Long chargerId, String chargerLocation, String chargerName, double latitude, double longitude, String chargingSpeed, List<ChargerType> chargerTypeList, String chargerRole, boolean favorite, double avgRate, String chargerStatus){
        this.chargerId = chargerId;
        this.chargerLocation = chargerLocation;
        this.chargerName = chargerName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.chargingSpeed = chargingSpeed;
        this.chargerTypeList = chargerTypeList;
        this.chargerRole = chargerRole;
        this.favorite = favorite;
        this.avgRate = avgRate;
        this.chargerStatus = chargerStatus;
    }

}
