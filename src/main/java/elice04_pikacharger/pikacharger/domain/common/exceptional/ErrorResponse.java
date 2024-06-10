package elice04_pikacharger.pikacharger.domain.common.exceptional;

import lombok.Getter;

// dto 역할을 수행함.
@Getter
public class ErrorResponse {
    private final String name;
    private final String message;

    public ErrorResponse(ErrorCode errorCode){
        this.name = errorCode.name();
        this.message = errorCode.getMessage();
    }
}
