package elice04_pikacharger.pikacharger.domain.image.dto.payload;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewImagePayload {
    private Long reviewId;
    private String imageUrl;
}
