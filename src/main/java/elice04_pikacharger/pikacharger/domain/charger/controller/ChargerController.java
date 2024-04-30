package elice04_pikacharger.pikacharger.domain.charger.controller;

import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerDetailResponseDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerEditResponseDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerResponseDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerSearchResponseDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.ChargerCreateDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.ChargerUpdateDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.LocationNameDto;
import elice04_pikacharger.pikacharger.domain.charger.service.ChargerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/charger")
public class ChargerController {

    private final ChargerService chargerService;

    @GetMapping("/{chargerId}")
    public ResponseEntity<ChargerDetailResponseDto> chargerDetail(@PathVariable Long chargerId) {
        ChargerDetailResponseDto chargerDetailResponseDto = chargerService.chargerDetail(chargerId);
        return new ResponseEntity<>(chargerDetailResponseDto, HttpStatus.OK);
    }

    @GetMapping("{chargerId}/edit")
    public ResponseEntity<ChargerEditResponseDto> getChargerEditForm(@PathVariable Long chargerId) {
        ChargerEditResponseDto chargerEditResponseDto = chargerService.chargerEditDetail(chargerId);
        return new ResponseEntity<>(chargerEditResponseDto, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<ChargerSearchResponseDto>> getChargerSearchList(@RequestParam String location) {
        List<ChargerSearchResponseDto> chargerSearchList = chargerService.chargerSearch(location);
        return new ResponseEntity<>(chargerSearchList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ChargerResponseDto> createCharger(@Valid @RequestBody ChargerCreateDto chargerCreateDto){
        ChargerResponseDto chargerResponseDto = chargerService.createCharger(chargerCreateDto);
        return new ResponseEntity<>(chargerResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/update/{chargerId}")
    public ResponseEntity<ChargerResponseDto> updateCharger(@Valid @RequestBody ChargerUpdateDto chargerUpdateDto, @PathVariable Long chargerId) {
        ChargerResponseDto chargerResponseDto = chargerService.updateCharger(chargerUpdateDto, chargerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{chargerId}")
    public ResponseEntity<?> deleteCharger(@PathVariable Long chargerId) {
        chargerService.deleteCharger(chargerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
