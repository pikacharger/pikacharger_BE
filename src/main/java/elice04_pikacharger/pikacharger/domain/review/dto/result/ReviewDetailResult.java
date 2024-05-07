package elice04_pikacharger.pikacharger.domain.review.dto.result;

import elice04_pikacharger.pikacharger.domain.image.domain.ReviewImage;
import elice04_pikacharger.pikacharger.domain.review.domain.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDetailResult extends ReviewResult{
    private String nickname;
    private String profileImage;
    private Boolean userIdMatch;

    public static ReviewDetailResult toDto(Review review, boolean userIdMatch) {
        ReviewDetailResult result = ReviewDetailResult.detailBuilder()
                .reviewId(review.getId())
                .chargerName(review.getCharger().getChargerName())
                .content(review.getContent())
                .rating(review.getRating())
                .createAt(review.getCreateDate())
                .nickname(review.getUser().getNickName())
                .profileImage("https://pikacharger-bucket.s3.ap-northeast-2.amazonaws.com/images/4ee7db00-2f7d-4375-b393-8239a75f44f1_%EB%B0%B0%EB%84%88.png")
                .imageUrls(review.getImgList().stream()
                        .map(ReviewImage::getImageUrl)
                        .collect(Collectors.toList()))
                .userIdMatch(userIdMatch)
                .build();
        return result;
    }

    @Builder(builderMethodName = "detailBuilder")
    public ReviewDetailResult(Long reviewId, String chargerName, String content, Integer rating,
                              List<String> imageUrls, String nickname, LocalDateTime createAt, String profileImage, Boolean userIdMatch) {
        super(reviewId, chargerName, content, rating, imageUrls, createAt);
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.userIdMatch = userIdMatch;
    }
}
