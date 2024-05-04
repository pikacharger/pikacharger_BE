package elice04_pikacharger.pikacharger.domain.review.service.impl;

import elice04_pikacharger.pikacharger.domain.charger.dto.payload.ChargerUpdateDto;
import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.charger.repository.ChargerRepository;
import elice04_pikacharger.pikacharger.domain.image.domain.ChargerImage;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ChargerRepository chargerRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final S3UploaderService s3UploaderService;
    private final ReviewResult reviewResult;


    @Override
    @Transactional
    public Long saveReview(ReviewPayload reviewPayload, List<MultipartFile> multipartFiles) throws IOException {

        User user = userRepository.findById(reviewPayload.getUserId()).orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));

        Charger charger = chargerRepository.findById(reviewPayload.getChargerId()).orElseThrow(() -> new NoSuchElementException("해당하는 충전소가 존재하지 않습니다"));

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
    public ReviewResult findByReviewId(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("해당 리뷰를 찾을 수 없습니다. Review ID: " + reviewId));

        return reviewResult.toDto(review);
    }

    @Override
    public ReviewDetailResult findByDetailToReviewId(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("해당 리뷰를 찾을 수 없습니다. Review ID: " + reviewId));

        return ReviewDetailResult.toDto(review);
    }

    @Override
    public List<ReviewResult> findByUserId(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        if (reviews.isEmpty()) {
            // 해당 사용자의 리뷰가 없을 경우 빈 리스트 반환
            return Collections.emptyList();
        }
        return reviews.stream()
                .map(ReviewResult::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResult> findByChargerId(Long chargerId) {
        List<Review> reviews = reviewRepository.findByChargerId(chargerId);
        if (reviews.isEmpty()) {
            // 해당 충전소의 리뷰가 없을 경우 빈 리스트 반환
            return Collections.emptyList();
        }
        return reviews.stream()
                .map(ReviewResult::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long modifiedReview(Long reviewId, ReviewModifyPayload reviewModifyPayload, Long userId, List<MultipartFile> multipartFiles) throws IOException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("리뷰를 찾을 수 없습니다."));

        Long reviewerId = review.getUser().getId();
        if(!reviewerId.equals(userId)){
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        review.getImgList().clear();
        imageImgUpload(review, multipartFiles);

        return review.update(reviewModifyPayload.getContent(), reviewModifyPayload.getRating());
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
        float totalStars = (float) reviewsForCharger.stream().mapToInt(Review::getRating).sum();

        float avgRate = reviewCount > 0 ? totalStars / reviewCount : 0;

        charger.updateAvgRate(avgRate);
    }

//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
//    public void deleteImage(ReviewDeletedEvent event) {
//        // 리뷰 삭제 이벤트 발생 시 처리할 로직을 작성합니다.
//        // 예를 들어, 로그를 남기거나 관리자에게 알림을 전송할 수 있습니다.
          // 추후 S3와 review 삭제의 생명주기를 맞추기위해 사용.
//    }
}
