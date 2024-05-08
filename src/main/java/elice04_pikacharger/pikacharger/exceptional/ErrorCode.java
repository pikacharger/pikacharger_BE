package elice04_pikacharger.pikacharger.exceptional;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EXPIRED_ACCESS_TOKEN (HttpStatus.UNAUTHORIZED, "만료된 jwt 토큰입니다.");

    private final HttpStatus status;
    private final String message;
}
