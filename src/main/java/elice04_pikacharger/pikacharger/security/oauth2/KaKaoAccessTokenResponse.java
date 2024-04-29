package elice04_pikacharger.pikacharger.security.oauth2;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KaKaoAccessTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

}
