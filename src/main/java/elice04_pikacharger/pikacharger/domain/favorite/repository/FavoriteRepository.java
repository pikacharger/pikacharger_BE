package elice04_pikacharger.pikacharger.domain.favorite.repository;

import elice04_pikacharger.pikacharger.domain.favorite.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByChargerId(Long chargerId);
}
