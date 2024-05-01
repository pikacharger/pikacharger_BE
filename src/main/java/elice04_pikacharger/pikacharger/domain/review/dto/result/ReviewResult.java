package elice04_pikacharger.pikacharger.domain.review.dto.result;

import elice04_pikacharger.pikacharger.domain.image.domain.ReviewImage;
import elice04_pikacharger.pikacharger.domain.review.domain.Review;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Getter
@Setter
@NoArgsConstructor
public class ReviewResult {
    private Long reviewId;
    private String chargerName;
    private String content;
    private Integer rating;
    private List<String> imageUrls = new ArrayList<>();
    private LocalDateTime createAt;

    public static ReviewResult toDto(Review review){
        ReviewResult result = ReviewResult.builder()
                .reviewId(review.getId())
                .chargerName(review.getCharger().getChargerName())
                .content(review.getContent())
                .rating(review.getRating())
                .build();

        List<String> imageUrls = review.getImgList().stream()
                .map(ReviewImage::getImageUrl)
                .collect(Collectors.toList());
        result.setImageUrls(imageUrls);
        return result;
    }

    @Builder
    private ReviewResult(Long reviewId, String chargerName, String content, Integer rating, List<String> imageUrls){
        this.reviewId = reviewId;
        this.chargerName = chargerName;
        this.content = content;
        this.rating = rating;
        this.imageUrls = imageUrls;
    }
}
