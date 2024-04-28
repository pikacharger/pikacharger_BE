package elice04_pikacharger.pikacharger.domain.review.dto.result;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReviewResult {
    private Long reviewId;
    private String chargerName;
    private String content;
    private Integer rating;
//    private List<MultipartFile> reviewImage = new ArrayList<>();
    private LocalDateTime createAt;
}
