package elice04_pikacharger.pikacharger.domain.charger.dto;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.chargertype.entity.ChargerType;
import elice04_pikacharger.pikacharger.domain.image.domain.ChargerImage;
import elice04_pikacharger.pikacharger.domain.review.domain.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChargerDetailResponseDto {

    private String chargerLocation;
    private String chargerName;
    private String chargingSpeed;
    private double latitude;
    private double longitude;
    private String content;
    private int personalPrice;
    private List<ChargerType> chargerTypeList;
//    private List<ChargerImage> chargerImageList;
//    private List<Review> reviewList;
    private boolean favorite;

    public static ChargerDetailResponseDto toDto(Charger charger, boolean favorite){
        return ChargerDetailResponseDto.builder()
                .chargerLocation(charger.getChargerLocation())
                .chargerName(charger.getChargerName())
                .chargingSpeed(charger.getChargingSpeed())
                .latitude(charger.getLatitude())
                .longitude(charger.getLongitude())
                .content(charger.getContent())
                .personalPrice(charger.getPersonalPrice())
                .favorite(favorite)
                .chargerTypeList(charger.getChargerTypes())
                .build();
    }

    @Builder
    private ChargerDetailResponseDto(String chargerLocation, String chargerName, String chargingSpeed, double latitude, double longitude, String content, int personalPrice, boolean favorite, List<ChargerType> chargerTypeList){
        this.chargerLocation = chargerLocation;
        this.chargerName = chargerName;
        this.chargingSpeed = chargingSpeed;
        this.latitude = latitude;
        this.longitude = longitude;
        this.content = content;
        this.personalPrice = personalPrice;
        this.favorite = favorite;
        this.chargerTypeList = chargerTypeList;
    }
}
