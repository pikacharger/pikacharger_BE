package elice04_pikacharger.pikacharger.domain.review.service;

import elice04_pikacharger.pikacharger.domain.review.dto.payload.ReviewPayload;
import elice04_pikacharger.pikacharger.domain.review.dto.payload.ReviewModifyPayload;
import elice04_pikacharger.pikacharger.domain.review.dto.result.ReviewResult;

import java.io.IOException;
import java.util.List;

public interface ReviewService {
    Long saveReview(ReviewPayload reviewDTO, List<String> imgPaths) throws IOException;

    ReviewResult findByReviewId(Long reviewId);

    List<ReviewResult> findByUserId(Long userId);

    List<ReviewResult> findByChargerId(Long chargerId);

    Long modifiedReview(Long reviewId, ReviewModifyPayload reviewModifyDTO, Long userId);

    Long deleteReview(Long reviewId, Long userId);
}
