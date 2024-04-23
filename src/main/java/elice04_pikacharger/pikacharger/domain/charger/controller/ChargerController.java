package elice04_pikacharger.pikacharger.domain.charger.controller;

import elice04_pikacharger.pikacharger.domain.charger.dto.ChargerRequestDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.ChargerCreateDto;
import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.charger.service.ChargerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/charger")
public class ChargerController {

    private final ChargerService chargerService;

    @PostMapping("/create")
    public ResponseEntity<ChargerRequestDto> createCharger(@Valid @RequestBody ChargerCreateDto chargerCreateDto){
        ChargerRequestDto chargerRequestDto = chargerService.createCharger(chargerCreateDto);
        return new ResponseEntity<>(chargerRequestDto, HttpStatus.CREATED);
    }
}
