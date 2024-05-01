package elice04_pikacharger.pikacharger.domain.charger.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupedChargerResponseDto {

    private String chargerLocation;
    private String chargerName;
    private List<ChargerSearchResponseDto> chargers;

}
