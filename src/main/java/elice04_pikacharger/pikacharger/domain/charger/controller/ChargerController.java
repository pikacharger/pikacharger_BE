package elice04_pikacharger.pikacharger.domain.charger.controller;

import elice04_pikacharger.pikacharger.domain.charger.service.ChargerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/charger")
public class ChargerController {

    private final ChargerService chargerService;

    @PostMapping("/create")
    public ResponseEntity<?> createCharger(){

        return ResponseEntity.status(HttpStatus.CREATED).body("생성완료");
    }
}
