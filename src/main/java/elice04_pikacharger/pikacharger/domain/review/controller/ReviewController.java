package elice04_pikacharger.pikacharger.domain.review.controller;

import elice04_pikacharger.pikacharger.domain.image.service.S3UploaderService;
import elice04_pikacharger.pikacharger.domain.review.dto.payload.ReviewModifyPayload;
import elice04_pikacharger.pikacharger.domain.review.dto.payload.ReviewPayload;
import elice04_pikacharger.pikacharger.domain.review.dto.result.ReviewResult;
import elice04_pikacharger.pikacharger.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
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
public class ReviewController {
    private final ReviewService reviewService;
    private final S3UploaderService s3UploaderService;

    //TODO 응답 코드 작성.
    @Operation(summary = "리뷰 등록", description = "리뷰를 등록합니다.")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createReview(
            @Valid @RequestPart("reviewPayload") ReviewPayload reviewPayload,
            @RequestPart("imgUrl") List<MultipartFile> multipartFiles) throws IOException {
        Long reviewId = reviewService.saveReview(reviewPayload, multipartFiles);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewId);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResult> getReviewById(@PathVariable Long reviewId) {
        ReviewResult reviewResult = reviewService.findByReviewId(reviewId);
        return ResponseEntity.ok(reviewResult);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<ReviewResult>> getReviewsByUserId(@PathVariable Long userId) {
        List<ReviewResult> reviewResults = reviewService.findByUserId(userId);
        return ResponseEntity.ok(reviewResults);
    }

    @GetMapping("/charger/{chargerId}")
    public ResponseEntity<List<ReviewResult>> getReviewsByChargerId(@PathVariable Long chargerId){
        List<ReviewResult> reviewResults = reviewService.findByChargerId(chargerId);
        return ResponseEntity.ok(reviewResults);
    }


    @PatchMapping("/{reviewId}")
    public ResponseEntity<Long> updateReview(@PathVariable Long reviewId, @RequestBody @Valid ReviewModifyPayload reviewModifyPayload, @RequestParam("userId") Long userId) {
        Long updatedReviewId = reviewService.modifiedReview(reviewId, reviewModifyPayload, userId);
        return ResponseEntity.ok(updatedReviewId);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Long> deleteReview(@PathVariable Long reviewId, @RequestParam("userId") Long userId) {
        Long deletedReviewId = reviewService.deleteReview(reviewId, userId);
        return ResponseEntity.ok(deletedReviewId);
    }

}
