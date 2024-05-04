package elice04_pikacharger.pikacharger.domain.charger.dto.payload;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.chargertype.dto.payload.ChargerTypeDto;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargerCreateDto {

    @NotBlank(message = "주소를 입력해주세요")
    private String chargerLocation;

    private String chargerName;

    @NotBlank(message = "충전속도를 입력해주세요")
    private String chargingSpeed;

    private String content;

    @Min(value = 0, message = "0원 이상 입력해주세요")
    private double personalPrice;

    private List<ChargerTypeDto> chargerTypeDtoList;

    public Charger toEntity(User user, double latitude, double Longitude){
        return Charger.personalChargerBuilder()
                .chargerLocation(this.chargerLocation)
                .chargerName(this.chargerName)
                .chargingSpeed(this.chargingSpeed)
                .latitude(latitude)
                .longitude(Longitude)
                .content(this.content)
                .personalPrice(this.personalPrice)
                .user(user)
                .build();
    }

}
