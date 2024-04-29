package elice04_pikacharger.pikacharger.domain.review.service.impl;

import elice04_pikacharger.pikacharger.domain.charger.repository.ChargerRepository;
import elice04_pikacharger.pikacharger.domain.review.domain.Review;
import elice04_pikacharger.pikacharger.domain.review.dto.payload.ReviewModifyPayload;
import elice04_pikacharger.pikacharger.domain.review.dto.payload.ReviewPayload;
import elice04_pikacharger.pikacharger.domain.review.dto.result.ReviewResult;
import elice04_pikacharger.pikacharger.domain.review.repository.ReviewRepository;
import elice04_pikacharger.pikacharger.domain.review.service.ReviewService;
import elice04_pikacharger.pikacharger.common.mapper.ReviewMapper;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import elice04_pikacharger.pikacharger.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ChargerRepository chargerRepository;
    private final ReviewMapper reviewMapper;


    @Override
    @Transactional
    public Long saveReview(ReviewPayload reviewPayload, Long userId) {
        //S3 이미지 저장
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));

        Review review = reviewRepository.save(
                Review.builder()
                        .usedCharger(chargerRepository.findById(reviewPayload.getChgr()).orElseThrow(() -> new NoSuchElementException("해당하는 충전소가 존재하지 않습니다")))
                        .content(reviewPayload.getContent())
                        .rating(reviewPayload.getRating())
                        .user(user)
                        .build());
        return review.getId();
    }

    @Override
    public ReviewResult findByReviewId(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("해당 리뷰를 찾을 수 없습니다."));
        return reviewMapper.toDto(review);
    }

    @Override
    public List<ReviewResult> findByUserId(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        if (reviews.isEmpty()) {
            // 해당 사용자의 리뷰가 없을 경우 빈 리스트 반환
            return Collections.emptyList();
        }
        return reviews.stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResult> findByChargerId(Long usedChargerId) {
        List<Review> reviews = reviewRepository.findByUsedChargerId(usedChargerId);
        if (reviews.isEmpty()) {
            // 해당 충전소의 리뷰가 없을 경우 빈 리스트 반환
            return Collections.emptyList();
        }
        return reviews.stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long modifiedReview(Long reviewId, ReviewModifyPayload reviewModifyPayload, Long userId) {
        if(!reviewRepository.findById(reviewId).orElseThrow(() -> new NoSuchElementException("리뷰를 찾을 수 없습니다."))
                .getUser().getId().equals(userId)){
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        return review.update(reviewModifyPayload);
    }

    @Override
    @Transactional
    public Long deleteReview(Long reviewId, Long userId) {
        if(!reviewRepository.findById((reviewId)).orElseThrow(() -> new NoSuchElementException("존재하지 않는 후기입니다."))
                .getUser().getId().equals(userId)){
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
        reviewRepository.deleteById(reviewId);
        return reviewId;
    }

//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
//    public void deleteImage(ReviewDeletedEvent event) {
//        // 리뷰 삭제 이벤트 발생 시 처리할 로직을 작성합니다.
//        // 예를 들어, 로그를 남기거나 관리자에게 알림을 전송할 수 있습니다.
          // 추후 S3와 review 삭제의 생명주기를 맞추기위해 사용.
//    }
}
