package elice04_pikacharger.pikacharger.domain.image.repository;

import elice04_pikacharger.pikacharger.domain.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
