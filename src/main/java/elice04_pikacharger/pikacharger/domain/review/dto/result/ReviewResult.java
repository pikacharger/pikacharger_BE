package elice04_pikacharger.pikacharger.domain.review.dto.result;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
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
    private Long chargerId;
    private String chargerName;
    private String content;
    private Integer rating;
    private List<String> imageUrls = new ArrayList<>();
    private LocalDateTime createAt;

    public static ReviewResult toDto(Review review){
        ReviewResult result = ReviewResult.builder()
                .reviewId(review.getId())
                .chargerId(review.getCharger().getId())
                .chargerName(review.getCharger().getChargerName())
                .content(review.getContent())
                .rating(review.getRating())
                .createAt(review.getCreateDate())
                .imageUrls(review.getImgList().stream()
                        .map(ReviewImage::getImageUrl)
                        .collect(Collectors.toList()))
                .build();
        return result;
    }

    @Builder
    protected ReviewResult(Long reviewId, String chargerName, String content, Integer rating, List<String> imageUrls, LocalDateTime createAt, Long chargerId){
        this.reviewId = reviewId;
        this.chargerName = chargerName;
        this.content = content;
        this.rating = rating;
        this.imageUrls = imageUrls;
        this.createAt = createAt;
        this.chargerId = chargerId;
    }

    public ReviewResult(String chargerName){
        this.chargerName = chargerName;
    }
}
