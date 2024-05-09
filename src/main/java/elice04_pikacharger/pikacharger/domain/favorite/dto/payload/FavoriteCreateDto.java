package elice04_pikacharger.pikacharger.domain.favorite.dto.payload;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.favorite.entity.Favorite;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FavoriteCreateDto {

    private Long chargerId;

    public Favorite toDto(User user, Charger charger) {
        return Favorite.builder()
                .user(user)
                .charger(charger)
                .build();
    }
}
