package elice04_pikacharger.pikacharger.domain.review.dto.payload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReviewPayload { //리뷰 생성시 사용
    private Long chargerId; // 충전기 아이디

    // 리뷰 정보
    private String content;
    private Integer rating;
}
