package elice04_pikacharger.pikacharger.domain.charger.service;

import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerDetailResponseDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerEditResponseDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerResponseDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerSearchResponseDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.ChargerCreateDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.ChargerUpdateDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.LocationNameDto;
import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.charger.geocoding.GeocodingAPI;
import elice04_pikacharger.pikacharger.domain.charger.repository.ChargerRepository;
import elice04_pikacharger.pikacharger.domain.chargertype.dto.payload.ChargerTypeDto;
import elice04_pikacharger.pikacharger.domain.chargertype.entity.ChargerType;
import elice04_pikacharger.pikacharger.domain.favorite.repository.FavoriteRepository;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import elice04_pikacharger.pikacharger.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChargerServiceImpl implements ChargerService {

    private final ChargerRepository chargerRepository;
    private final UserRepository userRepository;
    private final GeocodingAPI geocodingAPI;
    private final FavoriteRepository favoriteRepository;


    @Transactional
    @Override
    public ChargerResponseDto createCharger(ChargerCreateDto chargerCreateDto) {
        User user = userRepository.findById(chargerCreateDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("유저가 존재하지 않습니다."));
        /** Todo 1.이미지 저장 추가 */
        try{
            List<String> locations = geocodingAPI.coordinatePairs(chargerCreateDto.getChargerLocation());
            chargerCreateDto.setLatitude(Double.parseDouble(locations.get(0))); // 위도
            chargerCreateDto.setLongitude(Double.parseDouble(locations.get(1))); // 경도
        }catch (Exception e){
            throw new RuntimeException("예외 발생"+e.getMessage());
        }

        Charger charger = chargerCreateDto.toEntity(user);
        for (ChargerTypeDto chargerTypeDto : chargerCreateDto.getChargerTypeDtoList()){
            ChargerType chargerType = ChargerType.builder()
                    .type(chargerTypeDto.getType())
                    .charger(charger)
                    .build();
            charger.getChargerTypes().add(chargerType);
        }
        Charger savedCharger = chargerRepository.save(charger);

        return ChargerResponseDto.toDto(savedCharger);
    }

    @Transactional
    @Override
    public ChargerResponseDto updateCharger(ChargerUpdateDto chargerUpdateDto, Long chargerId, Long userId) {
        if (!chargerRepository.existsByIdAndUserId(chargerId, userId)){
            throw new IllegalStateException("충전소 수정권한이 없습니다.");
        }
        Charger charger = chargerRepository.findById(chargerId)
                .orElseThrow(() -> new EntityNotFoundException("충전소가 존재하지 않습니다."));
        try{
            List<String> locations = geocodingAPI.coordinatePairs(chargerUpdateDto.getChargerLocation());
            chargerUpdateDto.setLatitude(Double.parseDouble(locations.get(0))); // 위도
            chargerUpdateDto.setLongitude(Double.parseDouble(locations.get(1))); // 경도
        }catch (Exception e){
            log.debug("좌표를 받아올수 없습니다. 오류 코드: "+ e.getMessage());
        }

        charger.updateCharger(
                chargerUpdateDto.getChargerLocation(),
                chargerUpdateDto.getChargerName(),
                chargerUpdateDto.getChargingSpeed(),
                chargerUpdateDto.getLatitude(),
                chargerUpdateDto.getLongitude(),
                chargerUpdateDto.getContent(),
                chargerUpdateDto.getPersonalPrice()
                );

        charger.getChargerTypes().clear();
        for (ChargerTypeDto chargerTypeDto : chargerUpdateDto.getChargerTypeDtoList()){
            ChargerType chargerType = ChargerType.builder()
                    .type(chargerTypeDto.getType())
                    .charger(charger)
                    .build();
            charger.getChargerTypes().add(chargerType);
        }
        Charger updatedCharger = chargerRepository.save(charger);

        return ChargerResponseDto.toDto(updatedCharger);
    }

    @Override
    public void deleteCharger(Long chargerId, Long userId) {
        if (!chargerRepository.existsByIdAndUserId(chargerId, userId)){
            throw new IllegalStateException("충전소 삭제권한이 없습니다.");
        }
        Charger charger = chargerRepository.findById(chargerId)
                .orElseThrow(() -> new EntityNotFoundException("충전소가 존재하지 않습니다."));
        chargerRepository.delete(charger);
    }

    @Override
    public ChargerDetailResponseDto chargerDetail(Long chargerId) {
        Charger charger = chargerRepository.findById(chargerId)
                .orElseThrow(() -> new EntityNotFoundException("충전소가 존재하지 않습니다."));
        boolean favorite = favoriteRepository.existsByChargerId(chargerId);

        return ChargerDetailResponseDto.toDto(charger, favorite);
    }

    @Override
    public ChargerEditResponseDto chargerEditDetail(Long chargerId) {
        Charger charger = chargerRepository.findById(chargerId)
                .orElseThrow(() -> new EntityNotFoundException("충전소가 존재하지 않습니다."));

        return ChargerEditResponseDto.toDto(charger);
    }

    @Override
    public List<ChargerSearchResponseDto> chargerSearch(String location) {
        try{
            List<String> locations = geocodingAPI.coordinatePairs(location);
            List<Charger> chargerList = chargerRepository.findChargersNearby(Double.parseDouble(locations.get(0)),Double.parseDouble(locations.get(1)));
            return chargerList.stream()
                    .map(ChargerSearchResponseDto::toDto)
                    .toList();
        }catch (Exception e){
            log.debug("좌표를 받아올수 없습니다. 오류 코드: "+ e.getMessage());
        }
        return null;
    }
}
