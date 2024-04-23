package elice04_pikacharger.pikacharger.domain.charger.dto.payload;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.chargertype.dto.payload.PublicChargerTypeDataDto;
import lombok.Data;

import java.util.List;

@Data
public class PublicChargerDataDto {

    private String chargerLocation;
    private String chargerName;
    private String chargingSpeed;
    private String companyName;
    private List<PublicChargerTypeDataDto> publicChargerTypeDataDtoList;
    private String chargerStatus;
    private double latitude;
    private double longitude;

    //    따로 저장방식
    public Charger toEntity(){
        return Charger.publicChargerBuilder()
                .chargerLocation(this.chargerLocation)
                .chargerName(this.chargerName)
                .chargingSpeed(this.chargingSpeed)
                .companyName(this.companyName)
                .chargerStatus(this.chargerStatus)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .build();
    }
}
