package elice04_pikacharger.pikacharger.domain.common.exceptional;

import org.springframework.http.HttpStatus;

public enum CommentErrorCode implements ErrorCode{
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_NOT_FOUND", "Could not found comment id"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "You are not authorized to update this comment");


    private final HttpStatus status;
    private final String name;
    private final String message;

    CommentErrorCode(HttpStatus status,  String name, String message){
        this.status = status;
        this.name = name;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
