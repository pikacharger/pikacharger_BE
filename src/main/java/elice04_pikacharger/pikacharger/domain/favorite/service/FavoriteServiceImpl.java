package elice04_pikacharger.pikacharger.domain.favorite.service;

import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerResponseDto;
import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.charger.repository.ChargerRepository;
import elice04_pikacharger.pikacharger.domain.favorite.dto.payload.FavoriteCreateDto;
import elice04_pikacharger.pikacharger.domain.favorite.dto.payload.FavoriteResponseDto;
import elice04_pikacharger.pikacharger.domain.favorite.entity.Favorite;
import elice04_pikacharger.pikacharger.domain.favorite.repository.FavoriteRepository;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import elice04_pikacharger.pikacharger.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    @Override
    public List<FavoriteResponseDto> favoriteList(Long userId) {
        List<Favorite> favoriteList = favoriteRepository.findByUserId(userId);
        List<FavoriteResponseDto> favoriteResponseDtoList = new ArrayList<>();
        for (Favorite favorite : favoriteList) {
            Charger charger = favorite.getCharger();
            boolean favoriteCheck = favoriteRepository.existsByUserIdAndChargerId(userId, charger.getId());
            favoriteResponseDtoList.add(FavoriteResponseDto.toDto(charger, favoriteCheck));
        }
        return favoriteResponseDtoList;
    }

    @Transactional
    @Override
    public void deleteFavorite(Long favoriteId) {
        Favorite favorite = favoriteRepository.findById(favoriteId)
                .orElseThrow(()->new EntityNotFoundException("즐겨찾기를 찾을수 없습니다."));

        favoriteRepository.delete(favorite);
    }
}
