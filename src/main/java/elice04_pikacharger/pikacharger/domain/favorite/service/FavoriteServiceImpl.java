package elice04_pikacharger.pikacharger.domain.favorite.service;

import elice04_pikacharger.pikacharger.domain.charger.dto.GroupedChargerResponseDto;
import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.charger.repository.ChargerRepository;
import elice04_pikacharger.pikacharger.domain.favorite.dto.GroupedFavoriteResponseDto;
import elice04_pikacharger.pikacharger.domain.favorite.dto.payload.FavoriteCreateDto;
import elice04_pikacharger.pikacharger.domain.favorite.dto.FavoriteResponseDto;
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
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ChargerRepository chargerRepository;

    @Transactional
    @Override
    public void createFavorite(Long userId, FavoriteCreateDto favoriteCreateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을수 없습니다."));

        Charger charger = chargerRepository.findById(favoriteCreateDto.getChargerId())
                .orElseThrow(() -> new EntityNotFoundException("충전소를 찾을수 없습니다."));

        Favorite favorite = favoriteCreateDto.toDto(user, charger);
        favoriteRepository.save(favorite);
    }

    @Transactional
    @Override
    public List<GroupedFavoriteResponseDto> favoriteList(Long userId) {
        List<Favorite> favoriteList = favoriteRepository.findByUserId(userId);
        List<FavoriteResponseDto> favoriteResponseDtoList = new ArrayList<>();
        for (Favorite favorite : favoriteList) {
            Charger charger = favorite.getCharger();
            boolean favoriteCheck = favoriteRepository.existsByUserIdAndChargerId(userId, charger.getId());
            favoriteResponseDtoList.add(FavoriteResponseDto.toDto(charger, favoriteCheck));
        }

        Map<String, List<FavoriteResponseDto>> groupedByLocation = favoriteResponseDtoList.stream()
                .collect(Collectors.groupingBy(dto -> dto.getChargerLocation() + "|" + dto.getChargerName()));

        int groupId = 1;
        List<GroupedFavoriteResponseDto> groupedDtoList = new ArrayList<>();
        for (var entry : groupedByLocation.entrySet()) {
            String[] keyParts = entry.getKey().split("\\|", -1);
            String chargerLocation = keyParts[0];
            String chargerName = keyParts[1];

            GroupedFavoriteResponseDto groupedFavoriteResponseDto = new GroupedFavoriteResponseDto();
            groupedFavoriteResponseDto.setFavoriteGroupId(groupId++);
            groupedFavoriteResponseDto.setChargerLocation(chargerLocation);
            groupedFavoriteResponseDto.setChargerName(chargerName);
            groupedFavoriteResponseDto.setChargers(entry.getValue());
            groupedDtoList.add(groupedFavoriteResponseDto);
        }
        return groupedDtoList;
    }

    @Transactional
    @Override
    public void deleteFavorite(Long chargerId, Long userId) {
        if (!favoriteRepository.existsByChargerIdAndUserId(chargerId, userId)) {
            throw new IllegalStateException("즐겨찾기 삭제 권한이 없습니다.");
        }
        Favorite favorite = favoriteRepository.findByChargerIdAndUserId(chargerId, userId)
                .orElseThrow(()->new EntityNotFoundException("즐겨찾기를 찾을수 없습니다."));

        favoriteRepository.delete(favorite);
    }
}
