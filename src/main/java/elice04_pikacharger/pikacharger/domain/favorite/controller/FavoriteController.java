package elice04_pikacharger.pikacharger.domain.favorite.controller;

import elice04_pikacharger.pikacharger.domain.favorite.dto.payload.FavoriteCreateDto;
import elice04_pikacharger.pikacharger.domain.favorite.dto.payload.FavoriteResponseDto;
import elice04_pikacharger.pikacharger.domain.favorite.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/create")
    public ResponseEntity<?> createFavorite(@RequestBody FavoriteCreateDto favoriteCreateDto) {
        favoriteService.createFavorite(favoriteCreateDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<FavoriteResponseDto>> favoriteList(@PathVariable Long userId) {
        List<FavoriteResponseDto> favoriteResponseDtoList = favoriteService.favoriteList(userId);
        return new ResponseEntity<>(favoriteResponseDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<?> deleteFavorite(@PathVariable Long favoriteId) {
        favoriteService.deleteFavorite(favoriteId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
