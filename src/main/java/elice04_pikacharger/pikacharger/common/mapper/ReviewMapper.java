package elice04_pikacharger.pikacharger.common.mapper;

import elice04_pikacharger.pikacharger.domain.review.domain.Review;
import elice04_pikacharger.pikacharger.domain.review.dto.result.ReviewResult;

//해당 방식과 @Mapper 어노테이션을 이용한 MyBatis를 사용할지 고민중!
public interface ReviewMapper extends EntityMapper<ReviewResult, Review> {
}
