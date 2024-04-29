package elice04_pikacharger.pikacharger.security.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class OAuthProvider {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String GRANT_TYPE = "authorization_code";
    public static final String TOKEN_TYPE = "Bearer";

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String tokenRequestUri;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String memberInfoRequestUri;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    private final RestTemplate restTemplate;

    public KaKaoAccessTokenResponse getAccessToken(String authorizationCode) throws KakaoTokenRequestException, KakaoServerException {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", GRANT_TYPE);
            body.add("client_id", clientId);
            body.add("redirect_uri", redirectUri);
            body.add("code", authorizationCode);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, httpHeaders);

            return restTemplate.postForEntity(
                    tokenRequestUri, request, KaKaoAccessTokenResponse.class).getBody();
        } catch (HttpClientErrorException e) {
            throw new KakaoTokenRequestException(e.getMessage(),e);
        } catch (HttpServerErrorException e) {
            throw new KakaoServerException(e.getMessage());
        }
    }


}