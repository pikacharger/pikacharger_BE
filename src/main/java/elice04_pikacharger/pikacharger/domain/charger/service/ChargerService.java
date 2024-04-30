package elice04_pikacharger.pikacharger.domain.charger.service;

import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerDetailResponseDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerEditResponseDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerResponseDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerSearchResponseDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.ChargerCreateDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.ChargerUpdateDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.LocationNameDto;

import java.util.List;

public interface ChargerService {

    ChargerResponseDto createCharger(ChargerCreateDto chargerCreateDto);

    ChargerResponseDto updateCharger(ChargerUpdateDto chargerUpdateDto, Long chargerId);

    void deleteCharger(Long chargerId);

    ChargerDetailResponseDto chargerDetail(Long chargerId);

    ChargerEditResponseDto chargerEditDetail(Long chargerId);

    List<ChargerSearchResponseDto> chargerSearch(String location);
}
