package elice04_pikacharger.pikacharger.domain.common.mapper;

import elice04_pikacharger.pikacharger.domain.review.domain.Review;
import elice04_pikacharger.pikacharger.domain.review.dto.result.ReviewResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

//해당 방식과 @Mapper 어노테이션을 이용한 MyBatis를 사용할지 고민중!
@Mapper(componentModel = "spring")
public interface ReviewMapper extends EntityMapper<ReviewResult, Review> {
}
