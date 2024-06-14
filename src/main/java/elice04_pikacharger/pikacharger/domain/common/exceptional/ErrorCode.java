package elice04_pikacharger.pikacharger.domain.common.exceptional;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name();
    HttpStatus getStatus();
    String getMessage();
}
