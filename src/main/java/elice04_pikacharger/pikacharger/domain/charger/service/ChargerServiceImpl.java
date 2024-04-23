package elice04_pikacharger.pikacharger.domain.charger.service;

import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerRequestDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.ChargerCreateDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.ChargerUpdateDto;
import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.charger.geocoding.GeocodingAPI;
import elice04_pikacharger.pikacharger.domain.charger.repository.ChargerRepository;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import elice04_pikacharger.pikacharger.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChargerServiceImpl implements ChargerService {

    private final ChargerRepository chargerRepository;
    private final UserRepository userRepository;
    private final GeocodingAPI geocodingAPI;


    @Transactional
    @Override
    public ChargerRequestDto createCharger(ChargerCreateDto chargerCreateDto) {
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
        Charger savedCharger = chargerRepository.save(charger);

        return ChargerRequestDto.toDto(savedCharger);
    }

    @Transactional
    @Override
    public ChargerRequestDto updateCharger(ChargerUpdateDto chargerUpdateDto, Long chargerId) {
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
        Charger updatedCharger = chargerRepository.save(charger);

        return ChargerRequestDto.toDto(updatedCharger);
    }
}
