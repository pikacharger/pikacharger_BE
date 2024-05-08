package elice04_pikacharger.pikacharger.domain.favorite.controller;

import elice04_pikacharger.pikacharger.domain.favorite.dto.GroupedFavoriteResponseDto;
import elice04_pikacharger.pikacharger.domain.favorite.dto.payload.FavoriteCreateDto;
import elice04_pikacharger.pikacharger.domain.favorite.dto.FavoriteResponseDto;
import elice04_pikacharger.pikacharger.domain.favorite.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
@Tag(name = "(즐겨찾기)", description = "즐겨찾기 관련 api")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Operation(summary = "즐겨찾기 생성", description = "즐겨찾기 생성")
    @PostMapping("")
    public ResponseEntity<Void> createFavorite(@RequestBody FavoriteCreateDto favoriteCreateDto) {
        favoriteService.createFavorite(favoriteCreateDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "즐겨찾기 조회", description = "유저 id를 이용해 자신이 등록한 즐겨찾기 목록 조회")
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<GroupedFavoriteResponseDto>> favoriteList(@PathVariable Long userId) {
        List<GroupedFavoriteResponseDto> groupedFavoriteResponseDtoList = favoriteService.favoriteList(userId);
        return new ResponseEntity<>(groupedFavoriteResponseDtoList, HttpStatus.OK);
    }

    @Operation(summary = "즐겨찾기 삭제", description = "즐겨찾기 id와 유저 id를 이용해 자신이 만든 즐겨찾기일 경우 삭제")
    @DeleteMapping("/{favoriteId}/users/{userId}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long favoriteId, @PathVariable Long userId) {
        favoriteService.deleteFavorite(favoriteId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
