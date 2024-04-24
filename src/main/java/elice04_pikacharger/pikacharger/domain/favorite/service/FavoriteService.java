package elice04_pikacharger.pikacharger.domain.favorite.service;

import elice04_pikacharger.pikacharger.domain.favorite.dto.payload.FavoriteCreateDto;

public interface FavoriteService {

    void createFavorite(FavoriteCreateDto favoriteCreateDto);

}
