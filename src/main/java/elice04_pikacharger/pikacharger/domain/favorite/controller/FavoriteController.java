package elice04_pikacharger.pikacharger.domain.favorite.controller;

import elice04_pikacharger.pikacharger.domain.favorite.dto.payload.FavoriteCreateDto;
import elice04_pikacharger.pikacharger.domain.favorite.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
