package elice04_pikacharger.pikacharger.domain.image.repository;

import elice04_pikacharger.pikacharger.domain.image.domain.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserImageRepository extends JpaRepository<ProfileImage, Long> {
}
