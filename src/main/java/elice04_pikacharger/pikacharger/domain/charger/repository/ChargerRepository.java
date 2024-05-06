package elice04_pikacharger.pikacharger.domain.charger.repository;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargerRepository extends JpaRepository<Charger, Long> {
    @Query(value = "" +
            "SELECT *, " +
            "    (ST_Distance_Sphere(" +
            "        point(longitude, latitude), " +
            "        point(:longitude, :latitude)" +
            "    ) / 1000) AS distance " +
            "FROM charger " +
            "HAVING distance < 1 " +
            "ORDER BY distance ASC",
            nativeQuery = true)
    List<Charger> findChargersNearby(@Param("latitude") double latitude, @Param("longitude") double longitude);

    boolean existsByIdAndUserId(Long chargerId, Long userId);

    List<Charger> findByUserId(Long userId);

}
