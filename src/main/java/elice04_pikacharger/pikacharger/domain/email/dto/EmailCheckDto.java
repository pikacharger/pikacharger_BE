package elice04_pikacharger.pikacharger.domain.email.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EmailCheckDto {
    @Email
    @NotEmpty(message = "인증을 요청한 이메일을 입력해주세요")
    private String email;

    @NotEmpty(message = "인증 번호를 입력해주세요")
    private String authNum;

}
