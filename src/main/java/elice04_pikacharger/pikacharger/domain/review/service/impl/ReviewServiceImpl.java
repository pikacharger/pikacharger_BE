package elice04_pikacharger.pikacharger.domain.review.service.impl;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.charger.repository.ChargerRepository;
import elice04_pikacharger.pikacharger.domain.image.domain.ReviewImage;
import elice04_pikacharger.pikacharger.domain.image.repository.ReviewImageRepository;
import elice04_pikacharger.pikacharger.domain.image.service.S3UploaderService;
import elice04_pikacharger.pikacharger.domain.review.domain.Review;
import elice04_pikacharger.pikacharger.domain.review.dto.payload.ReviewModifyPayload;
import elice04_pikacharger.pikacharger.domain.review.dto.payload.ReviewPayload;
import elice04_pikacharger.pikacharger.domain.review.dto.result.ReviewDetailResult;
import elice04_pikacharger.pikacharger.domain.review.dto.result.ReviewResult;
import elice04_pikacharger.pikacharger.domain.review.repository.ReviewRepository;
import elice04_pikacharger.pikacharger.domain.review.service.ReviewService;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import elice04_pikacharger.pikacharger.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ChargerRepository chargerRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final S3UploaderService s3UploaderService;

    @Override
    @Transactional
    public Long saveReview(Long userId, ReviewPayload reviewPayload, List<MultipartFile> multipartFiles) throws IOException {

        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));

        Charger charger = chargerRepository.findById(reviewPayload.getChargerId()).orElseThrow(() -> new NoSuchElementException("해당하는 충전소가 존재하지 않습니다"));

        int rating = reviewPayload.getRating();
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("평점은 1에서 5 사이의 정수여야 합니다.");
        }

        Review review = reviewRepository.save(
                Review.builder()
                        .content(reviewPayload.getContent())
                        .rating(reviewPayload.getRating())
                        .user(user)
                        .charger(charger)
                        .build());

        //S3 이미지 저장
        List<String> imgPaths = new ArrayList<>();
        if (multipartFiles != null && !multipartFiles.isEmpty()) {
            imgPaths = s3UploaderService.uploadMultipleFiles(multipartFiles, "images");
        }

        for (String imgUrl : imgPaths) {
            ReviewImage img = new ReviewImage(imgUrl, review);
            reviewImageRepository.save(img);
        }

        // 평균 별점 계산 호출
        calculateAvgStar(review.getId(), charger.getId());
        return review.getId();
    }

    @Override
    public ReviewResult findByReviewId(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("해당 리뷰를 찾을 수 없습니다. Review ID: " + reviewId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));

        boolean userIdMatch = false;
        if (userId != null) {
            userIdMatch = chargerRepository.existsByIdAndUserId(reviewId, userId);
        }

        return ReviewDetailResult.toDto(review, userIdMatch);
    }

    @Override
    public ReviewDetailResult findByDetailToReviewId(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("해당 리뷰를 찾을 수 없습니다. Review ID: " + reviewId));

        Long userId = review.getUser().getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));

        boolean userIdMatch = false;
        if (userId != null) {
            userIdMatch = chargerRepository.existsByIdAndUserId(reviewId, userId);
        }

        return ReviewDetailResult.toDto(review, userIdMatch);
    }

    //TODO 유저 리뷰정보 반환 DTO 생성하기(리팩토링)
    @Override
    public List<ReviewResult> findByUserId(Long userId, PageRequest pageRequest) {
        Page<Review> reviewsPage = reviewRepository.findByUserId(userId, pageRequest);
        return reviewsPage.map(review -> ReviewResult.toDto(review)).getContent();
    }

    @Override
    public List<ReviewResult> findByChargerId(Long chargerId, PageRequest pageRequest) {
        Optional<Charger> charger = chargerRepository.findById(chargerId);
        Page<Review> reviewsPage = reviewRepository.findReviewByChargerId(chargerId, pageRequest);

        if(reviewsPage.isEmpty()){
            return Collections.singletonList(new ReviewResult(charger.get().getChargerName()));
        }
        return reviewsPage.map(review -> ReviewResult.toDto(review)).getContent();
    }

    @Override
    @Transactional
    public Long modifiedReview(Long reviewId, ReviewModifyPayload reviewModifyPayload, Long userId, List<MultipartFile> multipartFiles) throws IOException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("리뷰를 찾을 수 없습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));

        boolean userIdMatch = false;
        if (userId != null) {
            userIdMatch = chargerRepository.existsByIdAndUserId(reviewId, userId);
        }

        review.getImgList().clear();
        imageImgUpload(review, multipartFiles);

        return review.update(reviewModifyPayload.getContent(), reviewModifyPayload.getRating(), userIdMatch);
    }

    private void imageImgUpload(Review review, List<MultipartFile> multipartFiles) throws IOException {
        List<String> imgPaths = new ArrayList<>();
        if (multipartFiles != null && !multipartFiles.isEmpty()) {
            imgPaths = s3UploaderService.uploadMultipleFiles(multipartFiles, "images");
        }

        for (String imgUrl : imgPaths) {
            ReviewImage image = ReviewImage.builder()
                    .review(review)
                    .imageUrl(imgUrl)
                    .build();
            review.getImgList().add(image);
        }
    }

    @Override
    @Transactional
    public Long deleteReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 후기입니다."));

        Long reviewerId = review.getUser().getId();
        if (!reviewerId.equals(userId)) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

        List<ReviewImage> reviewImages = reviewImageRepository.findByReviewId(reviewId);
        for (ReviewImage reviewImage : reviewImages) {
            String imagePath = reviewImage.getImageUrl();
            s3UploaderService.deleteFile(imagePath);
        }
        reviewRepository.deleteById(reviewId);
        return reviewId;
    }

    @Override
    public void calculateAvgStar(Long reviewId, Long chargerId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + reviewId));
        Charger charger = chargerRepository.findById(chargerId)
                .orElseThrow(() -> new EntityNotFoundException("Charger not found with id: " + chargerId));

        List<Review> reviewsForCharger = reviewRepository.findByChargerId(chargerId);

        int reviewCount = reviewsForCharger.size();
        int totalStars = reviewsForCharger.stream().mapToInt(Review::getRating).sum();

        double avgRate = reviewCount > 0 ? (double) totalStars / reviewCount : 0;

        BigDecimal avgRateDecimal = BigDecimal.valueOf(avgRate)
                .setScale(1, RoundingMode.HALF_UP);
        double roundedAvgRate = avgRateDecimal.doubleValue();

        charger.updateAvgRate(roundedAvgRate);
    }

    // 유저의 총 리뷰 개수 반환
    public Long getTotalReviewsByUserId(Long userId) {
        return reviewRepository.countByUserId(userId);
    }

    // 충전소의 총 리뷰 개수 반환
    public Long getTotalReviewsByChargerId(Long chargerId) {
        return reviewRepository.countByChargerId(chargerId);
    }


//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
//    public void deleteImage(ReviewDeletedEvent event) {
//        // 리뷰 삭제 이벤트 발생 시 처리할 로직을 작성합니다.
//        // 예를 들어, 로그를 남기거나 관리자에게 알림을 전송할 수 있습니다.
          // 추후 S3와 review 삭제의 생명주기를 맞추기위해 사용.
//    }
}
