package elice04_pikacharger.pikacharger.domain.image.dto.payload;

import lombok.*;


// RequestBody가 아닌 ModelAttribute에 사용될 거면 setter추가!
@Getter
@Builder // 테스트를 위해서 추가!
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChargerImagePayload {
    private Long chargerId;
    private String imageUrl;
}
