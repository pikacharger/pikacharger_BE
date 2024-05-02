package elice04_pikacharger.pikacharger.domain.review.service;

import elice04_pikacharger.pikacharger.domain.review.dto.payload.ReviewPayload;
import elice04_pikacharger.pikacharger.domain.review.dto.payload.ReviewModifyPayload;
import elice04_pikacharger.pikacharger.domain.review.dto.result.ReviewDetailResult;
import elice04_pikacharger.pikacharger.domain.review.dto.result.ReviewResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ReviewService {
    Long saveReview(ReviewPayload reviewPayload, List<MultipartFile> multipartFiles) throws IOException;

    ReviewResult findByReviewId(Long reviewId);

    ReviewDetailResult findByDetailToReviewId(Long reviewId);

    List<ReviewResult> findByUserId(Long userId);

    List<ReviewResult> findByChargerId(Long chargerId);

    Long modifiedReview(Long reviewId, ReviewModifyPayload reviewModifyPayload, Long userId, List<MultipartFile> multipartFiles) throws IOException;

    Long deleteReview(Long reviewId, Long userId);

    void calculateAvgStar(Long reviewId, Long chargerId);
}
