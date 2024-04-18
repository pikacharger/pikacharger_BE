package elice04_pikacharger.pikacharger.domain.review.repository;


import elice04_pikacharger.pikacharger.domain.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
