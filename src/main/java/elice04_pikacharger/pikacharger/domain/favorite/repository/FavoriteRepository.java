package elice04_pikacharger.pikacharger.domain.favorite.repository;

import elice04_pikacharger.pikacharger.domain.favorite.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByUserIdAndChargerId(Long userId, Long chargerId);
    List<Favorite> findByUserId(Long userId);

    boolean existsByIdAndUserId(Long favoriteId, Long userId);

    boolean existsByChargerIdAndUserId(Long chargerId, Long userId);
}
