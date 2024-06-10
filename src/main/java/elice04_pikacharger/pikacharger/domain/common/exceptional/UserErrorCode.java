package elice04_pikacharger.pikacharger.domain.common.exceptional;

import org.json.HTTP;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ErrorCode{
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "Could not found user id"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "You are not authorized to update this comment"),
    USER_MISMATCH(HttpStatus.FORBIDDEN, "USER_MISMATCH", "The user does not match the comment author");

    private final HttpStatus status;
    private final String name;
    private final String message;

    UserErrorCode(HttpStatus status, String name, String message) {
        this.status = status;
        this.name = name;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
