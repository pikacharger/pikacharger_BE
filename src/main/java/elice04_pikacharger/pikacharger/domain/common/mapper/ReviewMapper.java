package elice04_pikacharger.pikacharger.domain.common.mapper;

import elice04_pikacharger.pikacharger.domain.review.domain.Review;
import elice04_pikacharger.pikacharger.domain.review.dto.result.ReviewResult;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component //빈 등록
public interface ReviewMapper extends EntityMapper<ReviewResult, Review> {
}