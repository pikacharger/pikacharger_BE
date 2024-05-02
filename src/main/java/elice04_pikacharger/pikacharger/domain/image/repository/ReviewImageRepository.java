package elice04_pikacharger.pikacharger.domain.image.repository;

import elice04_pikacharger.pikacharger.domain.image.domain.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
    List<ReviewImage> findByReviewId(Long reviewId);
}
