package elice04_pikacharger.pikacharger.domain.review.repository;


import elice04_pikacharger.pikacharger.domain.review.domain.Review;
import elice04_pikacharger.pikacharger.domain.review.dto.result.ReviewResult;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByChargerId(Long chargerId);
    Page<Review> findReviewByChargerId(Long chargerId, PageRequest pageRequest);
    Page<Review> findByUserId(Long userId, PageRequest pageRequest);
    Long countByUserId(Long userId);
    Long countByChargerId(Long chargerId);
    boolean existsByIdAndUserId(Long chargerId, Long userId);
}
