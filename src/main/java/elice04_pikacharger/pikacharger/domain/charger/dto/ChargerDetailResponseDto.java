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

    private Long chargerId;
    private String chargerLocation;
    private String chargerName;
    private String chargingSpeed;
    private double latitude;
    private double longitude;
    private String content;
    private double memberPrice;
    private double nonmemberPrice;
    private double personalPrice;
    private double avgRate;
    private String chargerRole;
    private List<ChargerType> chargerTypeList;
    private List<ChargerImage> chargerImageList;
    private List<Review> reviewList;
    private boolean favorite;
    private String chargerStatus;
    private boolean myChargerCheck;

    public static ChargerDetailResponseDto toDto(Charger charger, boolean favorite, boolean myChargerCheck){
        return ChargerDetailResponseDto.builder()
                .chargerId(charger.getId())
                .chargerLocation(charger.getChargerLocation())
                .chargerName(charger.getChargerName())
                .chargingSpeed(charger.getChargingSpeed())
                .latitude(charger.getLatitude())
                .longitude(charger.getLongitude())
                .content(charger.getContent())
                .memberPrice(charger.getMemberPrice())
                .nonmemberPrice(charger.getNonmemberPrice())
                .personalPrice(charger.getPersonalPrice())
                .avgRate(charger.getAvgRate())
                .chargerRole(charger.getChargerRole().getMessage())
                .favorite(favorite)
                .chargerTypeList(charger.getChargerTypes())
                .chargerStatus(charger.getChargerStatus())
                .chargerImageList(charger.getChargerImages())
                .reviewList(charger.getReviews())
                .myChargerCheck(myChargerCheck)
                .build();
    }

    @Builder
    private ChargerDetailResponseDto(Long chargerId, String chargerLocation, String chargerName, String chargingSpeed,
                                     double latitude, double longitude, String content, double memberPrice,
                                     double nonmemberPrice, double personalPrice, double avgRate, String chargerRole,
                                     boolean favorite, List<ChargerType> chargerTypeList, String chargerStatus,
                                     List<ChargerImage> chargerImageList, List<Review> reviewList, boolean myChargerCheck)
    {
        this.chargerId = chargerId;
        this.chargerLocation = chargerLocation;
        this.chargerName = chargerName;
        this.chargingSpeed = chargingSpeed;
        this.latitude = latitude;
        this.longitude = longitude;
        this.content = content;
        this.memberPrice = memberPrice;
        this.nonmemberPrice = nonmemberPrice;
        this.personalPrice = personalPrice;
        this.avgRate = avgRate;
        this.chargerRole = chargerRole;
        this.favorite = favorite;
        this.chargerTypeList = chargerTypeList;
        this.chargerStatus = chargerStatus;
        this.chargerImageList = chargerImageList;
        this.reviewList = reviewList;
        this.myChargerCheck = myChargerCheck;
    }
}
