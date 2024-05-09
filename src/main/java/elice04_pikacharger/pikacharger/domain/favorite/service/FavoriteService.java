package elice04_pikacharger.pikacharger.domain.favorite.service;

import elice04_pikacharger.pikacharger.domain.favorite.dto.GroupedFavoriteResponseDto;
import elice04_pikacharger.pikacharger.domain.favorite.dto.payload.FavoriteCreateDto;
import elice04_pikacharger.pikacharger.domain.favorite.dto.FavoriteResponseDto;

import java.util.List;

public interface FavoriteService {

    void createFavorite(Long userId, FavoriteCreateDto favoriteCreateDto);

    List<GroupedFavoriteResponseDto> favoriteList(Long userId);

    void deleteFavorite(Long chargerId, Long userId);
}
