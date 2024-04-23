package elice04_pikacharger.pikacharger.domain.charger.service;

import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerRequestDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.ChargerCreateDto;
import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;

public interface ChargerService {

    ChargerRequestDto createCharger(ChargerCreateDto chargerCreateDto);
}
