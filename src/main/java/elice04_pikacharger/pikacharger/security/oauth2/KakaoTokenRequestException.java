package elice04_pikacharger.pikacharger.security.oauth2;

import org.springframework.web.client.HttpClientErrorException;

public class KakaoTokenRequestException extends Throwable {
    public KakaoTokenRequestException(String s, HttpClientErrorException e) {
    }

}
