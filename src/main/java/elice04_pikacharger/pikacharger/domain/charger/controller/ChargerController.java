package elice04_pikacharger.pikacharger.domain.charger.controller;

import elice04_pikacharger.pikacharger.domain.charger.dto.*;
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
@RequestMapping("/chargers")
public class ChargerController {

    private final ChargerService chargerService;

    @GetMapping("/{chargerId}")
    public ResponseEntity<ChargerDetailResponseDto> chargerDetail(@PathVariable Long chargerId) {
        ChargerDetailResponseDto chargerDetailResponseDto = chargerService.chargerDetail(chargerId);
        return new ResponseEntity<>(chargerDetailResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{chargerId}/edit")
    public ResponseEntity<ChargerEditResponseDto> getChargerEditForm(@PathVariable Long chargerId) {
        ChargerEditResponseDto chargerEditResponseDto = chargerService.chargerEditDetail(chargerId);
        return new ResponseEntity<>(chargerEditResponseDto, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<GroupedChargerResponseDto>> getChargerSearchList(@RequestParam String location) {
        List<GroupedChargerResponseDto> groupedChargerResponseDtoList = chargerService.chargerSearch(location);
        return new ResponseEntity<>(groupedChargerResponseDtoList, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ChargerResponseDto> createCharger(@Valid @RequestBody ChargerCreateDto chargerCreateDto){
        ChargerResponseDto chargerResponseDto = chargerService.createCharger(chargerCreateDto);
        return new ResponseEntity<>(chargerResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{chargerId}/users/{userId}")
    public ResponseEntity<ChargerResponseDto> updateCharger(@Valid @RequestBody ChargerUpdateDto chargerUpdateDto, @PathVariable Long chargerId, @PathVariable Long userId) {
        ChargerResponseDto chargerResponseDto = chargerService.updateCharger(chargerUpdateDto, chargerId, userId);
        return new ResponseEntity<>(chargerResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{chargerId}/users/{userId}")
    public ResponseEntity<Void> deleteCharger(@PathVariable Long chargerId, @PathVariable Long userId) {
        chargerService.deleteCharger(chargerId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
