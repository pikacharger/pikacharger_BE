package elice04_pikacharger.pikacharger.domain.charger.dto.payload;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.chargertype.dto.payload.ChargerTypeDto;
import elice04_pikacharger.pikacharger.domain.image.domain.ChargerImage;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargerCreateDto {

    @NotNull
    private Long userId;

    @NotBlank(message = "주소를 입력해주세요")
    private String chargerLocation;

    @NotBlank(message = "상세주소를 입력해주세요")
    private String chargerName;

    @NotBlank(message = "주소를 입력해주세요")
    private String chargingSpeed;

    private double latitude;
    private double longitude;

    @NotBlank(message = "충전소&충전기의 설명을 추가해주세요")
    private String content;

    @Min(value = 0, message = "0원 이상 입력해주세요")
    private double personalPrice;

    private List<ChargerTypeDto> chargerTypeDtoList;
//    private List<MultipartFile> multipartFiles;

    public Charger toEntity(User user){
        return Charger.personalChargerBuilder()
                .chargerLocation(this.chargerLocation)
                .chargerName(this.chargerName)
                .chargingSpeed(this.chargingSpeed)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .content(this.content)
                .personalPrice(this.personalPrice)
                .user(user)
                .build();
    }

}
