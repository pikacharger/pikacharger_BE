package elice04_pikacharger.pikacharger.domain.charger.controller;

import elice04_pikacharger.pikacharger.domain.charger.dto.*;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.ChargerCreateDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.ChargerUpdateDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.LocationNameDto;
import elice04_pikacharger.pikacharger.domain.charger.service.ChargerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chargers")
@Tag(name = "(충전소)", description = "충전소 관련 api")
public class ChargerController {

    private final ChargerService chargerService;

    @Operation(summary = "충전소 상세 조회", description = "충전소 id를 이용한 충전소 상세 데이터 반환")
    @GetMapping("/{chargerId}")
    public ResponseEntity<ChargerDetailResponseDto> chargerDetail(@PathVariable Long chargerId) {
        ChargerDetailResponseDto chargerDetailResponseDto = chargerService.chargerDetail(chargerId);
        return new ResponseEntity<>(chargerDetailResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "충전소 수정폼 데이터 조회", description = "충전소 수정시 폼안에 들어갈 데이터 반환")
    @GetMapping("/{chargerId}/users/{userId}")
    public ResponseEntity<ChargerEditResponseDto> getChargerEditForm(@PathVariable Long chargerId, @PathVariable Long userId) {
        ChargerEditResponseDto chargerEditResponseDto = chargerService.chargerEditDetail(chargerId, userId);
        return new ResponseEntity<>(chargerEditResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "충전소 검색", description = "도로명, 지번 주소로 검색시 반경 2km 충전소 데이터 반환")
    @GetMapping("")
    public ResponseEntity<List<GroupedChargerResponseDto>> getChargerSearchList(@RequestParam String location) {
        List<GroupedChargerResponseDto> groupedChargerResponseDtoList = chargerService.chargerSearch(location);
        return new ResponseEntity<>(groupedChargerResponseDtoList, HttpStatus.OK);
    }

    @Operation(summary = "충전소 등록", description = "충전소 등록")
    @PostMapping("")
    public ResponseEntity<ChargerResponseDto> createCharger(@Valid ChargerCreateDto chargerCreateDto, @RequestPart("imgUrl") List<MultipartFile> multipartFiles) throws IOException {
        ChargerResponseDto chargerResponseDto = chargerService.createCharger(chargerCreateDto, multipartFiles);
        return new ResponseEntity<>(chargerResponseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "충전소 수정", description = "충전소 id와 유저 id를 이용해 자신이 만든 충전소일 경우 수정")
    @PatchMapping("/{chargerId}/users/{userId}")
    public ResponseEntity<ChargerResponseDto> updateCharger(@Valid @RequestBody ChargerUpdateDto chargerUpdateDto, @PathVariable Long chargerId, @PathVariable Long userId) {
        ChargerResponseDto chargerResponseDto = chargerService.updateCharger(chargerUpdateDto, chargerId, userId);
        return new ResponseEntity<>(chargerResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "충전소 삭제", description = "충전소 id와 유저 id를 이용해 자신이 만든 충전소일 경우 삭제")
    @DeleteMapping("/{chargerId}/users/{userId}")
    public ResponseEntity<Void> deleteCharger(@PathVariable Long chargerId, @PathVariable Long userId) {
        chargerService.deleteCharger(chargerId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
