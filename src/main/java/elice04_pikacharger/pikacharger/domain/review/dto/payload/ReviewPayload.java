package elice04_pikacharger.pikacharger.domain.review.dto.payload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReviewPayload { //리뷰 생성시 사용
    private Long reviewid; //리뷰 번호

    private Long uid; //유저 아이디

    private Long chgr; // 충전기 아이디

    // 회원 정보 (필요한가?)
    private Long id;
    private String nickname;

    // 충전소 정보 (필요한가?)
    private String charger_name;

    // 리뷰 정보
    private String content;
    private Integer rating;
    private List<MultipartFile> review_image = new ArrayList<>();
    private LocalDateTime createDate;
}
