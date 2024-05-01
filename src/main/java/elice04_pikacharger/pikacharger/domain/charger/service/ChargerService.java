package elice04_pikacharger.pikacharger.domain.charger.service;

import elice04_pikacharger.pikacharger.domain.charger.dto.*;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.ChargerCreateDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.ChargerUpdateDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.LocationNameDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ChargerService {

    ChargerResponseDto createCharger(ChargerCreateDto chargerCreateDto, List<MultipartFile> multipartFiles) throws IOException;

    ChargerResponseDto updateCharger(ChargerUpdateDto chargerUpdateDto, Long chargerId, Long userId);

    void deleteCharger(Long chargerId, Long userId);

    ChargerDetailResponseDto chargerDetail(Long chargerId);

    ChargerEditResponseDto chargerEditDetail(Long chargerId, Long userId);

    List<GroupedChargerResponseDto> chargerSearch(String location);
}
