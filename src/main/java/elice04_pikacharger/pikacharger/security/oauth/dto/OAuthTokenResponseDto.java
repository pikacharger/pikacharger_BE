package elice04_pikacharger.pikacharger.security.oauth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthTokenResponseDto {
    private Long userId;
    private String accessToken;
    private String refreshToken;

    @Builder
    public OAuthTokenResponseDto(Long userId, String accessToken, String refreshToken) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}

