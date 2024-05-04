package elice04_pikacharger.pikacharger.domain.favorite.dto;

import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerSearchResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupedFavoriteResponseDto {

    private int favoriteGroupId;
    private String chargerLocation;
    private String chargerName;
    private List<FavoriteResponseDto> chargers;
}
