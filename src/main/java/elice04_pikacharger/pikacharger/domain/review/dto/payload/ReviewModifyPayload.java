package elice04_pikacharger.pikacharger.domain.review.dto.payload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReviewModifyPayload {
    private String content;

    private String chargerName;

    private Integer rating;
}
