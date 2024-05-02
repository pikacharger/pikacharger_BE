package elice04_pikacharger.pikacharger.domain.review.controller;

import elice04_pikacharger.pikacharger.domain.image.service.S3UploaderService;
import elice04_pikacharger.pikacharger.domain.review.dto.payload.ReviewModifyPayload;
import elice04_pikacharger.pikacharger.domain.review.dto.payload.ReviewPayload;
import elice04_pikacharger.pikacharger.domain.review.dto.result.ReviewResult;
import elice04_pikacharger.pikacharger.domain.review.repository.ReviewRepository;
import elice04_pikacharger.pikacharger.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.bytecode.internal.bytebuddy.PrivateAccessorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@Tag(name = "(리뷰)", description = "리뷰 관련 api")
public class ReviewController {
    private final ReviewService reviewService;
    private final S3UploaderService s3UploaderService;
    private final ReviewRepository reviewRepository;

    //TODO 응답 코드 작성.
    @Operation(summary = "리뷰 등록", description = "리뷰를 등록합니다.")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createReview(
            @Valid @RequestPart("reviewPayload") ReviewPayload reviewPayload,
            @RequestPart(name = "imgUrl", required = false) List<MultipartFile> multipartFiles) throws IOException {
        Long reviewId = reviewService.saveReview(reviewPayload, multipartFiles);

        return ResponseEntity.status(HttpStatus.CREATED).body(reviewId);
    }

    @Operation(summary = "리뷰 수정 페이지 정보 조회", description = "리뷰 수정 페이지를 출력할 때 필요한 정보를 조회하는 api.")
    @GetMapping("/update/{reviewId}")
    public ResponseEntity<ReviewResult> getReviewById(@PathVariable Long reviewId) {
        ReviewResult reviewResult = reviewService.findByReviewId(reviewId);
        return ResponseEntity.ok(reviewResult);
    }

    @Operation(summary = "리뷰 상세페이지 정보 조회", description = "리뷰 상세 페이지를 출력할 때 필요한 정보를 조회하는 api.")
    @GetMapping("/deatil/{reviewId}")
    public ResponseEntity<ReviewResult> getReviewDetailById(@PathVariable Long reviewId) {
        ReviewResult reviewResult = reviewService.findByDetailToReviewId(reviewId);
        return ResponseEntity.ok(reviewResult);
    }

    @Operation(summary = "유저 리뷰 정보 조회", description = "userId로 리뷰 정보를 조회하는 api.")
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<ReviewResult>> getReviewsByUserId(@PathVariable Long userId) {
        List<ReviewResult> reviewResults = reviewService.findByUserId(userId);
        return ResponseEntity.ok(reviewResults);
    }

    @Operation(summary = "충전소 리뷰 정보 조회", description = "충전소에 등록되어있는 리뷰 정보를 조회하는 api.")
    @GetMapping("/charger/{chargerId}")
    public ResponseEntity<List<ReviewResult>> getReviewsByChargerId(@PathVariable Long chargerId){
        List<ReviewResult> reviewResults = reviewService.findByChargerId(chargerId);
        return ResponseEntity.ok(reviewResults);
    }

    @Operation(summary = "리뷰 수정", description = "리뷰를 수정합니다.")
    @PatchMapping(value = "/{reviewId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> updateReview(@PathVariable Long reviewId,
                                             @RequestPart @Valid ReviewModifyPayload reviewModifyPayload,
                                             @RequestParam("userId") Long userId,
                                             @RequestPart(name = "imgUrl", required = false) List<MultipartFile> multipartFiles) throws IOException {
        Long updatedReviewId = reviewService.modifiedReview(reviewId, reviewModifyPayload, userId, multipartFiles);
        return ResponseEntity.ok(updatedReviewId);
    }

    @Operation(summary = "리뷰 삭제", description = "리뷰 삭제 api.")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Long> deleteReview(@PathVariable Long reviewId, @RequestParam("userId") Long userId) {
        Long deletedReviewId = reviewService.deleteReview(reviewId, userId);
        return ResponseEntity.ok(deletedReviewId);
    }

}
