package elice04_pikacharger.pikacharger.domain.review.dto.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResultToUser {
    private List<ReviewResult> reviews;
    private Long totalReviews;
    private int currentPage;
    private int pageSize;
}
