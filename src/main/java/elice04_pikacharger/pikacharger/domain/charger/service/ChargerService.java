package elice04_pikacharger.pikacharger.domain.charger.service;

import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerDetailResponseDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerResponseDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.ChargerCreateDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.ChargerUpdateDto;

public interface ChargerService {

    ChargerResponseDto createCharger(ChargerCreateDto chargerCreateDto);

    ChargerResponseDto updateCharger(ChargerUpdateDto chargerUpdateDto, Long chargerId);

    void deleteCharger(Long chargerId);

    ChargerDetailResponseDto chargerDetail(Long chargerId);
}
