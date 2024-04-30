package elice04_pikacharger.pikacharger.domain.charger.service;

import elice04_pikacharger.pikacharger.domain.charger.dto.*;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.ChargerCreateDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.ChargerUpdateDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.LocationNameDto;

import java.util.List;

public interface ChargerService {

    ChargerResponseDto createCharger(ChargerCreateDto chargerCreateDto);

    ChargerResponseDto updateCharger(ChargerUpdateDto chargerUpdateDto, Long chargerId, Long userId);

    void deleteCharger(Long chargerId, Long userId);

    ChargerDetailResponseDto chargerDetail(Long chargerId);

    ChargerEditResponseDto chargerEditDetail(Long chargerId);

    List<GroupedChargerResponseDto> chargerSearch(String location);
}
