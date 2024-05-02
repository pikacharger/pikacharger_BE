package elice04_pikacharger.pikacharger.security.oauth2.OauthUser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuth2Provider {
    GOOGLE("google"),
    KAKAO("kakao");

    private final String registrationId;
}