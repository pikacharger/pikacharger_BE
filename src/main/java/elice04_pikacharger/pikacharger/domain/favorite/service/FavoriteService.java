package elice04_pikacharger.pikacharger.domain.favorite.service;

import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerResponseDto;
import elice04_pikacharger.pikacharger.domain.favorite.dto.payload.FavoriteCreateDto;
import elice04_pikacharger.pikacharger.domain.favorite.dto.payload.FavoriteResponseDto;

import java.util.List;

public interface FavoriteService {

    void createFavorite(FavoriteCreateDto favoriteCreateDto);

    List<FavoriteResponseDto> favoriteList(Long userId);

}
