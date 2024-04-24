package elice04_pikacharger.pikacharger.domain.favorite.service;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.charger.repository.ChargerRepository;
import elice04_pikacharger.pikacharger.domain.favorite.dto.payload.FavoriteCreateDto;
import elice04_pikacharger.pikacharger.domain.favorite.entity.Favorite;
import elice04_pikacharger.pikacharger.domain.favorite.repository.FavoriteRepository;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import elice04_pikacharger.pikacharger.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ChargerRepository chargerRepository;

    @Transactional
    @Override
    public void createFavorite(FavoriteCreateDto favoriteCreateDto) {
        User user = userRepository.findById(favoriteCreateDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을수 없습니다."));

        Charger charger = chargerRepository.findById(favoriteCreateDto.getChargerId())
                .orElseThrow(() -> new EntityNotFoundException("충전소를 찾을수 없습니다."));

        Favorite favorite = favoriteCreateDto.toDto(user, charger);
        favoriteRepository.save(favorite);
    }
}
