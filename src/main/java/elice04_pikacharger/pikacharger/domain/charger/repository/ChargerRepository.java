package elice04_pikacharger.pikacharger.domain.charger.repository;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargerRepository extends JpaRepository<Charger, Long> {
}
