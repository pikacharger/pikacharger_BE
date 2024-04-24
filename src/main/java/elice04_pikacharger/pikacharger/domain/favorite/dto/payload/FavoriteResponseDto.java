package elice04_pikacharger.pikacharger.domain.favorite.dto.payload;

import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerDetailResponseDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerResponseDto;
import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.charger.entity.ChargerRole;
import elice04_pikacharger.pikacharger.domain.chargertype.entity.ChargerType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FavoriteResponseDto {

    private String chargerLocation;
    private String chargerName;
    private double latitude;
    private double longitude;
    private List<ChargerType> chargerTypeList;
    private String chargerRole;
    private boolean favorite;
    private double avgRate;

    public static FavoriteResponseDto toDto(Charger charger, boolean favorite){
        return FavoriteResponseDto.builder()
                .chargerLocation(charger.getChargerLocation())
                .chargerName(charger.getChargerName())
                .latitude(charger.getLatitude())
                .longitude(charger.getLongitude())
                .chargerTypeList(charger.getChargerTypes())
                .chargerRole(charger.getChargerRole().getMessage())
                .favorite(favorite)
                .avgRate(charger.getAvgRate())
                .build();
    }

    @Builder
    private FavoriteResponseDto(String chargerLocation, String chargerName, double latitude, double longitude, List<ChargerType> chargerTypeList, String chargerRole, boolean favorite, double avgRate){
        this.chargerLocation = chargerLocation;
        this.chargerName = chargerName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.chargerTypeList = chargerTypeList;
        this.chargerRole = chargerRole;
        this.favorite = favorite;
        this.avgRate = avgRate;
    }

}
